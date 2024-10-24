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
class IncidenceMatrixGraphTest {

    /**
     * add vertex test.
     */
    @Test
    public void testAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 3);

        graph.addVertex(3);

        assertEquals(4, graph.vertexCount);
        assertEquals(0, graph.getNeighbors(3).size());
    }

    /**
     * remove vertex test.
     */
    @Test
    public void testRemoveVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(4, 4);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(0, 2);

        List<Integer> neighbors1 = graph.getNeighbors(1);
        assertTrue(neighbors1.contains(0));
        assertTrue(neighbors1.contains(2));

        // Удаляем вершину 1
        graph.removeVertex(1);

        List<Integer> neighbors2 = graph.getNeighbors(2);
        assertFalse(neighbors2.contains(1));
    }

    /**
     * add edge test.
     */
    @Test
    public void testAddEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 3);
        graph.addEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertTrue(neighbors.contains(1));
    }

    /**
     * remove edge test.
     */
    @Test
    public void testRemoveEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 3);
        graph.addEdge(0, 1);
        graph.removeEdge(0, 1);
        List<Integer> neighbors = graph.getNeighbors(0);
        assertFalse(neighbors.contains(1));
    }

    /**
     * top sort test.
     */
    @Test
    public void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(4, 4);
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
        IncidenceMatrixGraph graph1 = new IncidenceMatrixGraph(3, 3);
        graph1.addEdge(0, 1);

        IncidenceMatrixGraph graph2 = new IncidenceMatrixGraph(3, 3);
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

        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(4, 3);
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
