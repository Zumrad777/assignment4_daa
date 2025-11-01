package graph.topo;
import graph.common.*;
import java.util.*;
public class KahnTopo {
    private final Graph dag;
    private final Metrics M;
    public KahnTopo(Graph dag) {
        this(dag, null);
    }
    public KahnTopo(Graph dag, Metrics M) {
        this.dag = dag;
        this.M = M;
    }

    public List<Integer> order() {
        int n = dag.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) {
            for (Edge e : dag.neighbors(u)) {
                indeg[e.to()]++;
            }
        }

        Deque<Integer> q = new ArrayDeque<>();
        for (int v = 0; v < n; v++) {
            if (indeg[v] == 0) {
                q.add(v);
                if (M != null) M.kahnPushes++; // добавили в очередь
            }
        }

        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.remove();
            if (M != null) M.kahnPops++; // достали из очереди
            topo.add(u);

            for (Edge e : dag.neighbors(u)) {
                if (--indeg[e.to()] == 0) {
                    q.add(e.to());
                    if (M != null) M.kahnPushes++; // добавили новую вершину
                }
            }
        }

        if (topo.size() != n) {
            throw new IllegalStateException("Graph is not a DAG!");
        }

        return topo;
    }
}
