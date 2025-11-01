package graph;

import graph.common.*;
import graph.dagsp.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DagSpTests {

    @Test
    void testShortestPath() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(0, 2, 5);
        g.addEdge(2, 3, 1);

        DagShortestPaths sp = new DagShortestPaths(g, null);
        sp.run(0);

        assertEquals(3.0, sp.distTo(2), 1e-9);
        assertEquals(4.0, sp.distTo(3), 1e-9);
    }

    @Test
    void testLongestPath() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        g.addEdge(1, 4, 1);

        DagLongestPath lp = new DagLongestPath(g);
        lp.run();
        var crit = lp.critical();

        assertTrue(crit.getValue() >= 5); // длина пути
        assertNotNull(lp.pathTo(crit.getKey())); // есть критический путь
    }
}
