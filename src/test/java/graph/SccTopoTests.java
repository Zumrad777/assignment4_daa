package graph;
import graph.common.*;
import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class SccTopoTests {

    @Test
    void testSimpleSCC() {
        Graph g = new Graph(3);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(2,0,1);
        TarjanSCC scc = new TarjanSCC(g, null);
        int[] comp = scc.compute();
        assertEquals(comp[0], comp[1]);
        assertEquals(comp[1], comp[2]);
    }

    @Test
    void testTopoOrder() {
        Graph g = new Graph(4);
        g.addEdge(0,1,1);
        g.addEdge(1,2,1);
        g.addEdge(0,3,1);
        KahnTopo topo = new KahnTopo(g, null);
        var ord = topo.order();
        assertTrue(ord.indexOf(0) < ord.indexOf(1));
        assertTrue(ord.indexOf(0) < ord.indexOf(3));
    }
}
