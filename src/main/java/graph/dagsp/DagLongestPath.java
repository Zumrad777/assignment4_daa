package graph.dagsp;
import graph.common.*;
import graph.topo.*;
import java.util.*;
public class DagLongestPath {
    private final Graph dag;
    private final Metrics M;  // метрики (может быть null)
    private double[] best;
    private int[] prev;


    public DagLongestPath(Graph dag) {
        this(dag, null);
    }


    public DagLongestPath(Graph dag, Metrics M) {
        this.dag = dag;
        this.M = M;
    }

    public void run() {
        int n = dag.size();
        best = new double[n];
        prev = new int[n];
        Arrays.fill(best, Double.NEGATIVE_INFINITY);
        Arrays.fill(prev, -1);

        boolean[] hasIn = new boolean[n];
        for (int u = 0; u < n; u++)
            for (Edge e : dag.neighbors(u))
                hasIn[e.to()] = true;

        for (int v = 0; v < n; v++)
            if (!hasIn[v]) best[v] = 0;

        KahnTopo topo = new KahnTopo(dag);
        List<Integer> order = topo.order();

        for (int u : order) {
            if (best[u] == Double.NEGATIVE_INFINITY) continue;

            for (Edge e : dag.neighbors(u)) {
                double nd = best[u] + e.w();
                if (nd > best[e.to()]) {
                    best[e.to()] = nd;
                    prev[e.to()] = u;
                    if (M != null) M.relaxations++; // считаем обновления
                }
            }
        }
    }

    public Map.Entry<Integer, Double> critical() {
        int n = dag.size();
        int end = -1;
        double len = Double.NEGATIVE_INFINITY;
        for (int v = 0; v < n; v++) {
            if (best[v] > len) {
                len = best[v];
                end = v;
            }
        }
        return Map.entry(end, len);
    }

    public List<Integer> pathTo(int v) {
        List<Integer> path = new ArrayList<>();
        for (int x = v; x != -1; x = prev[x]) path.add(x);
        Collections.reverse(path);
        return path;
    }
}
