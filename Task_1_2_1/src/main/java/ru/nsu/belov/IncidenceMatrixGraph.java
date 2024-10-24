package ru.nsu.belov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Incidence Matrix Graph.
 */
public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    int vertexCount;
    private int edgeCount;

    /**
     * constructor.
     *
     * @param vertices int.
     * @param edges int.
     */
    public IncidenceMatrixGraph(int vertices, int edges) {
        this.vertexCount = vertices;
        this.edgeCount = edges;
        this.incidenceMatrix = new int[vertices][edges];
    }

    /**
     * add vertex.
     *
     * @param vertex int.
     */
    @Override
    public void addVertex(int vertex) {
        if (vertex >= vertexCount) {
            int newSize = vertex + 1;
            int[][] newMatrix = new int[newSize][edgeCount];
            for (int i = 0; i < vertexCount; i++) {
                newMatrix[i] = incidenceMatrix[i];
            }
            incidenceMatrix = newMatrix;
            vertexCount = newSize;
        }
    }

    /**
     * remove vertex.
     *
     * @param vertex int.
     */
    @Override
    public void removeVertex(int vertex) {
        if (vertex >= vertexCount) return;
        for (int i = 0; i < edgeCount; i++) {
            incidenceMatrix[vertex][i] = 0;
        }
    }

    /**
     * add edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    @Override
    public void addEdge(int vertex1, int vertex2) {
        if (edgeCount == 0) {
            return;
        }
        int edge = 0;
        for (; edge < edgeCount; edge++) {
            if (incidenceMatrix[vertex1][edge] == 0 && incidenceMatrix[vertex2][edge] == 0) {
                incidenceMatrix[vertex1][edge] = 1;
                incidenceMatrix[vertex2][edge] = -1;
                break;
            }
        }
    }

    /**
     * remove edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    @Override
    public void removeEdge(int vertex1, int vertex2) {
        for (int i = 0; i < edgeCount; i++) {
            if ((incidenceMatrix[vertex1][i] == 1 && incidenceMatrix[vertex2][i] == -1)
                    || (incidenceMatrix[vertex1][i] == -1 && incidenceMatrix[vertex2][i] == 1)) {
                incidenceMatrix[vertex1][i] = 0;
                incidenceMatrix[vertex2][i] = 0;
                break;
            }
        }
    }

    /**
     * get neighbors.
     *
     * @param vertex int.
     *
     * @return list integer.
     */
    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] == 1 || incidenceMatrix[vertex][i] == -1) {
                for (int j = 0; j < vertexCount; j++) {
                    if (j != vertex && incidenceMatrix[j][i] != 0) {
                        neighbors.add(j);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * read from file.
     *
     * @param filename string.
     */
    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int edge = 0;
            while ((line = br.readLine()) != null) {
                String[] vertices = line.split(" ");
                int vertex1 = Integer.parseInt(vertices[0]);
                int vertex2 = Integer.parseInt(vertices[1]);
                addEdge(vertex1, vertex2);
                edge++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * to string.
     *
     * @return string.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vertexCount; i++) {
            sb.append(i).append(": ");
            for (int j = 0; j < edgeCount; j++) {
                sb.append(incidenceMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * equals.
     *
     * @param obj object.
     *
     * @return boolean.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof IncidenceMatrixGraph)) {
            return false;
        }
        IncidenceMatrixGraph other = (IncidenceMatrixGraph) obj;
        for (int i = 0; i < vertexCount; i++) {
            if (!java.util.Arrays.equals(this.incidenceMatrix[i], other.incidenceMatrix[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * top sort.
     *
     * @return list integer.
     */
    @Override
    public List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];

        for (int i = 0; i < vertexCount; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, result);
            }
        }
        return result;
    }

    /**
     * helper.
     *
     * @param vertex int.
     * @param visited boolean arr.
     * @param result list integer.
     */
    private void topologicalSortUtil(int vertex, boolean[] visited, List<Integer> result) {
        visited[vertex] = true;

        for (int i = 0; i < edgeCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (incidenceMatrix[vertex][i] != 0 && !visited[j]) {
                    topologicalSortUtil(j, visited, result);
                }
            }
        }

        result.add(0, vertex);
    }
}
