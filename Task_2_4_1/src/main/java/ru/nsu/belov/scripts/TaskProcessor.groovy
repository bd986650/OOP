package ru.nsu.belov.scripts

import org.gradle.tooling.GradleConnector
import org.jsoup.Jsoup
import org.apache.commons.io.FileUtils
import ru.nsu.belov.RepositoryManager
import ru.nsu.belov.model.Student
import ru.nsu.belov.model.Task

class ProjectAnalyzer {
    private final String baseDir
    private final Map<String, Student> students
    private final List<Task> tasks
    private final Map<String, Map<String, Map<String, String>>> analysisResults

    ProjectAnalyzer(String baseDir, Map<String, Student> students, List<Task> tasks) {
        this.baseDir = baseDir
        this.students = students
        this.tasks = tasks
        this.analysisResults = [:]
    }

    void initialize() {
        new RepositoryManager().cleanRepositories()
        fetchRepositories()
    }

    private void fetchRepositories() {
        students.each { id, student ->
            def targetPath = "${baseDir}/${id}"
            def command = "git clone ${student.repository} ${targetPath}"
            def process = command.execute()
            process.waitFor()
        }
    }

    Map<String, Map<String, Map<String, String>>> analyze() {
        tasks.each { task ->
            analysisResults[task.id] = analyzeTask(task)
        }
        return analysisResults
    }

    private Map<String, Map<String, String>> analyzeTask(Task task) {
        def results = [:]
        students.each { id, student ->
            results[id] = analyzeStudentTask(id, task)
        }
        return results
    }

    private Map<String, String> analyzeStudentTask(String studentId, Task task) {
        def projectPath = "${baseDir}/${studentId}/${task.id}"
        def connector = GradleConnector.newConnector()
        connector.forProjectDirectory(new File(projectPath))
        def connection = connector.connect()
        def build = connection.newBuild()

        return [
            build: checkBuild(build),
            test: checkTests(build, projectPath),
            javadoc: checkJavadoc(build)
        ]
    }

    private String checkBuild(def build) {
        try {
            build.forTasks('build').run()
            return '+'
        } catch (Exception e) {
            return '-'
        }
    }

    private String checkTests(def build, String projectPath) {
        try {
            build.forTasks('test').addArguments('-i').run()
            def testReport = new File("${projectPath}/build/reports/tests/test/index.html")
            if (testReport.exists()) {
                def document = Jsoup.parse(testReport)
                return document.getElementById("successRate").select("div.percent").first().text()
            }
            return '-'
        } catch (Exception e) {
            return '-'
        }
    }

    private String checkJavadoc(def build) {
        try {
            build.forTasks('javadoc').run()
            return '+'
        } catch (Exception e) {
            return '-'
        }
    }
}

class ReportGenerator {
    private final Map<String, Map<String, Map<String, String>>> data

    ReportGenerator(Map<String, Map<String, Map<String, String>>> data) {
        this.data = data
    }

    void generate() {
        def template = new ReportTemplate(data)
        def report = template.render()
        FileUtils.writeStringToFile(new File("./result.html"), report)
    }
}

class ReportTemplate {
    private final Map<String, Map<String, Map<String, String>>> data
    private final Map<String, String> statusColors = [
        success: '#10b981',
        error: '#ef4444',
        warning: '#f59e0b'
    ]

    ReportTemplate(Map<String, Map<String, Map<String, String>>> data) {
        this.data = data
    }

    String render() {
        """
        <!DOCTYPE html>
        <html lang="ru">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Анализ проектов</title>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
            <style>
                ${generateStyles()}
            </style>
        </head>
        <body>
            <div class="container">
                ${generateHeader()}
                ${generateStats()}
                ${generateTaskList()}
            </div>
            <script>
                ${generateScript()}
            </script>
        </body>
        </html>
        """
    }

    private String generateStyles() {
        """
        :root {
            --primary: #2563eb;
            --primary-dark: #1d4ed8;
            --success: ${statusColors.success};
            --danger: ${statusColors.error};
            --warning: ${statusColors.warning};
            --light: #f8fafc;
            --dark: #1e293b;
            --gray: #64748b;
            --light-gray: #e2e8f0;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Inter', sans-serif;
            line-height: 1.6;
            color: var(--dark);
            background-color: #f1f5f9;
            padding: 2rem;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 1rem;
            box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
            overflow: hidden;
        }
        
        header {
            background: linear-gradient(135deg, var(--primary), var(--primary-dark));
            color: white;
            padding: 2rem;
            text-align: center;
        }
        
        h1 {
            font-size: 2rem;
            font-weight: 700;
            margin-bottom: 0.5rem;
        }
        
        .stats-bar {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            background: var(--light);
            padding: 1.5rem;
            border-bottom: 1px solid var(--light-gray);
        }
        
        .stat-item {
            text-align: center;
            padding: 1rem;
            background: white;
            border-radius: 0.5rem;
            box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1);
        }
        
        .stat-value {
            font-size: 1.5rem;
            font-weight: 700;
            color: var(--primary);
            margin-bottom: 0.25rem;
        }
        
        .stat-label {
            color: var(--gray);
            font-size: 0.875rem;
        }
        
        .task-list {
            padding: 1.5rem;
        }
        
        .task-item {
            margin-bottom: 1rem;
            border-radius: 0.75rem;
            overflow: hidden;
            box-shadow: 0 1px 3px 0 rgb(0 0 0 / 0.1);
            transition: all 0.3s ease;
            border: 1px solid var(--light-gray);
        }
        
        .task-header {
            background: white;
            padding: 1rem 1.5rem;
            cursor: pointer;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid var(--light-gray);
        }
        
        .task-title {
            font-size: 1.125rem;
            font-weight: 600;
        }
        
        .task-status {
            padding: 0.375rem 0.75rem;
            border-radius: 9999px;
            font-size: 0.75rem;
            font-weight: 600;
            text-transform: uppercase;
        }
        
        .status-completed {
            background-color: rgba(16, 185, 129, 0.1);
            color: var(--success);
        }
        
        .status-failed {
            background-color: rgba(239, 68, 68, 0.1);
            color: var(--danger);
        }
        
        .status-warning {
            background-color: rgba(245, 158, 11, 0.1);
            color: var(--warning);
        }
        
        .task-content {
            display: none;
            background: white;
            padding: 1.5rem;
        }
        
        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
        }
        
        th, td {
            padding: 0.75rem 1rem;
            text-align: left;
            border-bottom: 1px solid var(--light-gray);
        }
        
        th {
            background-color: var(--light);
            font-weight: 600;
            color: var(--gray);
            text-transform: uppercase;
            font-size: 0.75rem;
            letter-spacing: 0.05em;
        }
        
        tr:hover {
            background-color: var(--light);
        }
        
        .status-cell {
            font-weight: 500;
        }
        
        .status-success {
            color: var(--success);
        }
        
        .status-error {
            color: var(--danger);
        }
        
        .status-warning {
            color: var(--warning);
        }
        """
    }

    private String generateHeader() {
        """
        <header>
            <h1>Анализ проектов</h1>
        </header>
        """
    }

    private String generateStats() {
        def totalTasks = data.size()
        def successfulBuilds = data.values().sum { taskResults -> 
            taskResults.values().count { it.build == '+' }
        }
        def successfulTests = data.values().sum { taskResults -> 
            taskResults.values().count { it.test != '-' && it.test != 'Ошибка тестов' && it.test != 'Нет результатов' }
        }
        def successfulJavadoc = data.values().sum { taskResults -> 
            taskResults.values().count { it.javadoc == '+' }
        }

        """
        <div class="stats-bar">
            <div class="stat-item">
                <div class="stat-value">${totalTasks}</div>
                <div class="stat-label">Всего задач</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${successfulBuilds}</div>
                <div class="stat-label">Успешных сборок</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${successfulTests}</div>
                <div class="stat-label">Успешных тестов</div>
            </div>
            <div class="stat-item">
                <div class="stat-value">${successfulJavadoc}</div>
                <div class="stat-label">Успешных javadoc</div>
            </div>
        </div>
        """
    }

    private String generateTaskList() {
        """
        <div class="task-list">
            ${data.collect { task, taskResults ->
                def allSuccess = taskResults.values().every { it.build == '+' && it.javadoc == '+' && it.test != '-' && it.test != 'Ошибка тестов' && it.test != 'Нет результатов' }
                def statusClass = allSuccess ? 'status-completed' : 'status-failed'
                def statusText = allSuccess ? 'Успешно' : 'Неуспешно'
                
                """
                <div class="task-item">
                    <div class="task-header">
                        <div class="task-title">${task}</div>
                        <div class="task-status ${statusClass}">${statusText}</div>
                    </div>
                    <div class="task-content">
                        <table>
                            <thead>
                                <tr>
                                    <th>Студент</th>
                                    <th>Сборка</th>
                                    <th>Тесты</th>
                                    <th>Javadoc</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${taskResults.collect { studentId, result ->
                                    def buildStatus = result.build == '+' ? 'status-success' : 
                                                    result.build.startsWith('Ошибка') ? 'status-error' : 'status-warning'
                                    def testStatus = result.test == '-' ? 'status-warning' : 
                                                   result.test.startsWith('Ошибка') || result.test == 'Нет результатов' ? 'status-error' : 'status-success'
                                    def javadocStatus = result.javadoc == '+' ? 'status-success' : 
                                                      result.javadoc.startsWith('Ошибка') ? 'status-error' : 'status-warning'
                                    
                                    """
                                    <tr>
                                        <td>${studentId}</td>
                                        <td class="status-cell ${buildStatus}">${result.build}</td>
                                        <td class="status-cell ${testStatus}">${result.test}</td>
                                        <td class="status-cell ${javadocStatus}">${result.javadoc}</td>
                                    </tr>
                                    """
                                }.join('\n')}
                            </tbody>
                        </table>
                    </div>
                </div>
                """
            }.join('\n')}
        </div>
        """
    }

    private String generateScript() {
        """
        document.querySelectorAll('.task-header').forEach(header => {
            header.addEventListener('click', () => {
                const content = header.nextElementSibling;
                content.style.display = content.style.display === 'none' ? 'block' : 'none';
            });
        });
        
        document.addEventListener('DOMContentLoaded', function() {
            const firstTask = document.querySelector('.task-item');
            if (firstTask) {
                const firstContent = firstTask.querySelector('.task-content');
                firstContent.style.display = 'block';
            }
        });
        """
    }
}

def students = binding.getVariable("students")
def tasks = binding.getVariable("tasks")

def analyzer = new ProjectAnalyzer("reps", students, tasks)
analyzer.initialize()
def results = analyzer.analyze()

def reportGenerator = new ReportGenerator(results)
reportGenerator.generate()

binding.setVariable("results", results)
return results