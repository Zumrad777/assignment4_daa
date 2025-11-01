package graph.app;
import graph.common.*;
import graph.scc.*;
import graph.topo.*;
import graph.dagsp.*;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {

        // --- Load Graph ---
        Graph g = GraphIO.load("data/tasks.json");
        System.out.println("Graph loaded successfully!");
        System.out.println("Number of vertices: " + g.size());
        for (int i = 0; i < g.size(); i++) {
            for (Edge e : g.neighbors(i)) {
                System.out.println(e.from() + " -> " + e.to() + " (w=" + e.w() + ")");
            }
        }

        // --- Metrics ---
        Metrics m = new Metrics();

        // --- SCC (Tarjan) ---
        m.resetCounters();
        m.start();
        TarjanSCC scc = new TarjanSCC(g, m);
        int[] comp = scc.compute();
        m.stop();
        System.out.printf("%nSCC computed in %.4f ms.%n", m.getTimeMs());
        System.out.printf("(dfsVisits=%d, edges=%d)%n", m.dfsVisits, m.dfsEdgeScans);

        // Группировка вершин по компонентам
        Map<Integer, List<Integer>> groups = new HashMap<>();
        for (int v = 0; v < g.size(); v++) {
            groups.computeIfAbsent(comp[v], k -> new ArrayList<>()).add(v);
        }

        System.out.println("\nStrongly Connected Components:");
        for (var e : groups.entrySet()) {
            System.out.println("Component " + e.getKey() + " (size=" + e.getValue().size() + "): " + e.getValue());
        }
        System.out.println("Total components: " + scc.components());

        // --- Condensation DAG ---
        m.start();
        Graph dag = Graph.condensation(g, comp, scc.components());
        m.stop();
        System.out.printf("%nCondensation DAG built in %.4f ms. Vertices: %d%n", m.getTimeMs(), dag.size());

        // --- Topological Sort ---
        m.resetCounters();
        m.start();
        KahnTopo topo = new KahnTopo(dag, m);
        List<Integer> topoOrder = topo.order();
        m.stop();
        System.out.printf("%nTopological sort completed in %.4f ms.%n", m.getTimeMs());
        System.out.printf("(pushes=%d, pops=%d)%n", m.kahnPushes, m.kahnPops);
        System.out.println("Topological order of components: " + topoOrder);

        // Derived order of original tasks
        System.out.println("\nDerived order of original tasks (by components):");
        for (int cid : topoOrder) {
            List<Integer> vs = groups.getOrDefault(cid, List.of());
            Collections.sort(vs);
            System.out.println("C" + cid + " -> " + vs);
        }

        // --- Shortest Paths ---
        m.resetCounters();
        m.start();
        System.out.println("\nShortest paths in DAG (source = 0):");
        DagShortestPaths sp = new DagShortestPaths(dag, m);
        sp.run(0);
        m.stop();
        System.out.printf("Shortest paths computed in %.4f ms.%n", m.getTimeMs());
        System.out.printf("(relaxations=%d)%n", m.relaxations);
        for (int v = 0; v < dag.size(); v++) {
            System.out.println("dist(0 -> " + v + ") = " + sp.distTo(v)
                    + ", path = " + sp.pathTo(v));
        }

        // --- Longest Path / Critical Path ---
        m.resetCounters();
        m.start();
        DagLongestPath lp = new DagLongestPath(dag, m);
        lp.run();
        m.stop();
        System.out.printf("%nLongest path (critical path) computed in %.4f ms.%n", m.getTimeMs());
        System.out.printf("(updates=%d)%n", m.relaxations);
        var crit = lp.critical();
        System.out.println("Critical Path length = " + crit.getValue());
        System.out.println("Critical Path: " + lp.pathTo(crit.getKey()));

        // --- Summary ---
        System.out.println("\n=== Summary ===");
        System.out.println("SCC components: " + scc.components());
        System.out.println("Condensation DAG vertices: " + dag.size());
        System.out.println("Topo order size: " + topoOrder.size());
        System.out.println("Critical Path length: " + crit.getValue());
        System.out.printf("(dfsVisits=%d, edges=%d, pushes=%d, pops=%d, relaxations=%d)%n",
                m.dfsVisits, m.dfsEdgeScans, m.kahnPushes, m.kahnPops, m.relaxations);
    }
}
