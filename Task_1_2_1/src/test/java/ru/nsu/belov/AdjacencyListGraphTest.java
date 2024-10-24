package ru.nsu.belov;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * test.
 */
class AdjacencyListGraphTest {

    /**
     * add vertex test.
     */
    @Test
    public void testAddVertex() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    /**
     * remove vertex test.
     */
    @Test
    public void testRemoveVertex() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        graph.removeVertex(1);

        assertFalse(graph.getNeighbors(1).contains(0));
    }

    /**
     * add edge test.
     */
    @Test
    public void testAddEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(1, neighbors.size());
        assertTrue(neighbors.contains(1));
    }

    /**
     * test adding edges between existing vertices.
     */
    @Test
    public void testAddMultipleEdges() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.addEdge(0, 1);

        List<Integer> neighbors = graph.getNeighbors(0);
        assertEquals(1, neighbors.size());
    }

    /**
     * remove edge test.
     */
    @Test
    public void testRemoveEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        assertFalse(graph.getNeighbors(0).contains(1));
    }

    /**
     * test removing a non-existing edge.
     */
    @Test
    public void testRemoveNonExistingEdge() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.removeEdge(0, 1);

        assertTrue(graph.getNeighbors(0).isEmpty());
    }

    /**
     * top sort test.
     */
    @Test
    public void testTopologicalSort() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 2);
        graph.addEdge(2, 3);

        List<Integer> expected = Arrays.asList(0, 1, 2, 3);
        List<Integer> result = graph.topologicalSort();
        assertEquals(expected, result);
    }

    /**
     * equals test.
     */
    @Test
    public void testEquals() {
        AdjacencyListGraph graph1 = new AdjacencyListGraph();
        graph1.addVertex(0);
        graph1.addVertex(1);
        graph1.addEdge(0, 1);

        AdjacencyListGraph graph2 = new AdjacencyListGraph();
        graph2.addVertex(0);
        graph2.addVertex(1);
        graph2.addEdge(0, 1);

        assertTrue(graph1.equals(graph2));
    }

    /**
     * file test.
     *
     * @throws IOException exception.
     */
    @Test
    public void testReadFromFile() throws IOException {
        Path tempFile = Files.createTempFile("graph", ".txt");

        String content = "0 1 2\n1 2\n2 3\n";
        Files.write(tempFile, content.getBytes());

        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.readFromFile(tempFile.toString());

        List<Integer> neighbors0 = graph.getNeighbors(0);
        assertTrue(neighbors0.contains(1));
        assertTrue(neighbors0.contains(2));

        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertTrue(neighbors1.contains(2));

        List<Integer> neighbors2 = graph.getNeighbors(2);
        assertTrue(neighbors2.contains(3));

        Files.delete(tempFile);
    }
}
