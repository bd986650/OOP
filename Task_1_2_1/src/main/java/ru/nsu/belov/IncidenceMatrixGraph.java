package ru.nsu.belov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    int vertexCount;
    private int edgeCount;

    public IncidenceMatrixGraph(Integer vertices, Integer edges) {
        this.vertexCount = vertices;
        this.edgeCount = edges;
        this.incidenceMatrix = new int[vertices][edges];
    }

    @Override
    public void addVertex(Integer vertex) {
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

    @Override
    public void removeVertex(Integer vertex) {
        if (vertex >= vertexCount) {
            return;
        }

        for (int i = 0; i < edgeCount; i++) {
            if (incidenceMatrix[vertex][i] != 0) {
                for (int j = 0; j < vertexCount; j++) {
                    incidenceMatrix[j][i] = 0;
                }
            }
        }

        int[][] newMatrix = new int[vertexCount - 1][edgeCount];
        for (int i = 0, newRow = 0; i < vertexCount; i++) {
            if (i != vertex) {
                newMatrix[newRow++] = incidenceMatrix[i];
            }
        }
        incidenceMatrix = newMatrix;
        vertexCount--;
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2) {
        if (edgeCount == 0) {
            return;
        }

        int edge = 0;
        for (; edge < edgeCount; edge++) {
            if (incidenceMatrix[vertex1][edge] == 0 && incidenceMatrix[vertex2][edge] == 0) {
                incidenceMatrix[vertex1][edge] = 1;
                if (vertex1 == vertex2) {
                    incidenceMatrix[vertex2][edge] = 1;
                } else {
                    incidenceMatrix[vertex2][edge] = -1;
                }
                break;
            }
        }
    }

    @Override
    public void removeEdge(Integer vertex1, Integer vertex2) {
        for (int i = 0; i < edgeCount; i++) {
            if ((incidenceMatrix[vertex1][i] == 1 && incidenceMatrix[vertex2][i] == -1)
                    || (incidenceMatrix[vertex1][i] == -1 && incidenceMatrix[vertex2][i] == 1)) {
                incidenceMatrix[vertex1][i] = 0;
                incidenceMatrix[vertex2][i] = 0;
                break;
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(Integer vertex) {
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
            throw new RuntimeException("Error reading file: " + filename, e);
        }
    }

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
