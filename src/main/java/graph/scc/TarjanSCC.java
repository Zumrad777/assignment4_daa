package graph.scc;
import graph.common.*;
import java.util.*;
public class TarjanSCC {
    private final Graph g;
    private final Metrics M;
    private int time = 0, compCount = 0;
    private int[] disc, low, compId;
    private boolean[] onStack;
    private Deque<Integer> stack = new ArrayDeque<>();

    public TarjanSCC(Graph g) {
        this(g, null);
    }


    public TarjanSCC(Graph g, Metrics M) {
        this.g = g;
        this.M = M;
    }


    public int[] compute() {
        int n = g.size();
        disc = new int[n];
        low = new int[n];
        compId = new int[n];
        Arrays.fill(disc, -1);
        onStack = new boolean[n];

        for (int v = 0; v < n; v++) {
            if (disc[v] == -1) dfs(v);
        }
        return compId;
    }

    private void dfs(int u) {
        if (M != null) M.dfsVisits++;

        disc[u] = low[u] = time++;
        stack.push(u);
        onStack[u] = true;

        for (Edge e : g.neighbors(u)) {
            if (M != null) M.dfsEdgeScans++;
            int v = e.to();

            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }


        if (low[u] == disc[u]) {
            while (true) {
                int v = stack.pop();
                onStack[v] = false;
                compId[v] = compCount;
                if (v == u) break;
            }
            compCount++;
        }
    }

    public int components() {
        return compCount;
    }
}
