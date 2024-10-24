package ru.nsu.belov;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Adjacency List Graph.
 */
public class AdjacencyListGraph implements Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    /**
     * Constructor.
     */
    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    /**
     * add vertex.
     *
     * @param vertex int.
     */
    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * remove vertex.
     *
     * @param vertex int.
     */
    @Override
    public void removeVertex(int vertex) {
        adjacencyList.values().forEach(e -> e.remove(Integer.valueOf(vertex)));
        adjacencyList.remove(vertex);
    }

    /**
     * add edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    @Override
    public void addEdge(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
    }

    /**
     * remove edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    @Override
    public void removeEdge(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).remove(Integer.valueOf(vertex2));
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
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
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
            while ((line = br.readLine()) != null) {
                String[] edges = line.split(" ");
                int vertex = Integer.parseInt(edges[0]);
                addVertex(vertex);
                for (int i = 1; i < edges.length; i++) {
                    int connectedVertex = Integer.parseInt(edges[i]);
                    addEdge(vertex, connectedVertex);
                }
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
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
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
        if (!(obj instanceof AdjacencyListGraph)) {
            return false;
        }
        AdjacencyListGraph other = (AdjacencyListGraph) obj;
        return this.adjacencyList.equals(other.adjacencyList);
    }

    /**
     * top sort.
     *
     * @return list integer.
     */
    @Override
    public List<Integer> topologicalSort() {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Boolean> visited = new HashMap<>();

        for (Integer vertex : adjacencyList.keySet()) {
            visited.put(vertex, false);
        }

        for (Integer vertex : adjacencyList.keySet()) {
            if (!visited.get(vertex)) {
                topologicalSortUtil(vertex, visited, result);
            }
        }

        return result;
    }

    /**
     * helper.
     *
     * @param vertex int.
     * @param visited map integer , boolean.
     * @param result list integer.
     */
    private void topologicalSortUtil(Integer vertex, Map<Integer, Boolean> visited, List<Integer> result) {
        visited.put(vertex, true);

        for (Integer neighbor : adjacencyList.get(vertex)) {
            if (!visited.get(neighbor)) {
                topologicalSortUtil(neighbor, visited, result);
            }
        }

        result.add(0, vertex);
    }
}
