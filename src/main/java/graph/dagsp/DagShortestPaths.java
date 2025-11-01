package graph.dagsp;
import graph.common.*;
import graph.topo.*;
import java.util.*;


public class DagShortestPaths {
    private final Graph dag;
    private final Metrics M;  // метрики (может быть null)
    private double[] dist;
    private int[] prev;


    public DagShortestPaths(Graph dag) {
        this(dag, null);
    }

    public DagShortestPaths(Graph dag, Metrics M) {
        this.dag = dag;
        this.M = M;
    }

    public void run(int src) {
        int n = dag.size();
        dist = new double[n];
        prev = new int[n];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        Arrays.fill(prev, -1);
        dist[src] = 0;


        KahnTopo topo = new KahnTopo(dag);
        List<Integer> order = topo.order();
        for (int u : order) {
            if (dist[u] == Double.POSITIVE_INFINITY) continue;

            for (Edge e : dag.neighbors(u)) {
                double nd = dist[u] + e.w();
                if (nd < dist[e.to()]) {
                    dist[e.to()] = nd;
                    prev[e.to()] = u;
                    if (M != null) M.relaxations++;
                }
            }
        }
    }


    public double distTo(int v) {
        return dist[v];
    }


    public List<Integer> pathTo(int v) {
        if (Double.isInfinite(dist[v])) return List.of();
        List<Integer> path = new ArrayList<>();
        for (int x = v; x != -1; x = prev[x]) path.add(x);
        Collections.reverse(path);
        return path;
    }
}
