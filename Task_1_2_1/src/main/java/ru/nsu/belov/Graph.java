package ru.nsu.belov;

import java.util.List;

public interface Graph {

    void addVertex(Integer vertex);

    void removeVertex(Integer vertex);

    void addEdge(Integer vertex1, Integer vertex2);

    void removeEdge(Integer vertex1, Integer vertex2);

    List<Integer> getNeighbors(Integer vertex);

    void readFromFile(String filename);

    String toString();

    boolean equals(Object obj);

    List<Integer> topologicalSort();
}
