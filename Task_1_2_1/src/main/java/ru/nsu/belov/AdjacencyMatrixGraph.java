package ru.nsu.belov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacencyMatrixGraph implements Graph {
    private int[][] adjacencyMatrix;
    int vertexCount;

    public AdjacencyMatrixGraph(Integer size) {
        this.vertexCount = size;
        this.adjacencyMatrix = new int[size][size];
    }

    @Override
    public void addVertex(Integer vertex) {
        if (vertex >= vertexCount) {
            int newSize = vertex + 1;
            int[][] newMatrix = new int[newSize][newSize];
            for (int i = 0; i < vertexCount; i++) {
                newMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], newSize);
            }
            adjacencyMatrix = newMatrix;
            vertexCount = newSize;
        }
    }

    @Override
    public void removeVertex(Integer vertex) {
        if (vertex >= vertexCount) {
            return;
        }
        for (int i = 0; i < vertexCount; i++) {
            adjacencyMatrix[vertex][i] = 0;
            adjacencyMatrix[i][vertex] = 0;
        }
    }

    @Override
    public void addEdge(Integer vertex1, Integer vertex2) {
        if (vertex1 < vertexCount && vertex2 < vertexCount) {
            adjacencyMatrix[vertex1][vertex2] = 1;
        }
    }

    @Override
    public void removeEdge(Integer vertex1, Integer vertex2) {
        if (vertex1 < vertexCount && vertex2 < vertexCount) {
            adjacencyMatrix[vertex1][vertex2] = 0;
        }
    }

    @Override
    public List<Integer> getNeighbors(Integer vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int vertex = 0;
            while ((line = br.readLine()) != null) {
                String[] edges = line.split(" ");
                addVertex(vertex);
                for (String edge : edges) {
                    int connectedVertex = Integer.parseInt(edge);
                    addEdge(vertex, connectedVertex);
                }
                vertex++;
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
            for (int j = 0; j < vertexCount; j++) {
                sb.append(adjacencyMatrix[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AdjacencyMatrixGraph)) {
            return false;
        }
        AdjacencyMatrixGraph other = (AdjacencyMatrixGraph) obj;
        return Arrays.deepEquals(this.adjacencyMatrix, other.adjacencyMatrix);
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

        for (int i = 0; i < vertexCount; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && !visited[i]) {
                topologicalSortUtil(i, visited, result);
            }
        }

        result.add(0, vertex);
    }
}
