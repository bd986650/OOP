package ru.nsu.belov;

import java.util.List;

/**
 * interface graph.
 */
public interface Graph {

    /**
     * add vertex.
     *
     * @param vertex int.
     */
    void addVertex(int vertex);

    /**
     * remove vertex.
     *
     * @param vertex int.
     */
    void removeVertex(int vertex);

    /**
     * add edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    void addEdge(int vertex1, int vertex2);

    /**
     * remove edge.
     *
     * @param vertex1 int.
     * @param vertex2 int.
     */
    void removeEdge(int vertex1, int vertex2);

    /**
     * get neighbors.
     *
     * @param vertex int.
     *
     * @return list int.
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * read from file.
     *
     * @param filename string.
     */
    void readFromFile(String filename);

    /**
     * to string.
     *
     * @return string.
     */
    String toString();

    /**
     * equals.
     *
     * @param obj object.
     *
     * @return boolean.
     */
    boolean equals(Object obj);

    /**
     * topolog sort.
     *
     * @return List Integer.
     */
    List<Integer> topologicalSort();
}
