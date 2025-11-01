package graph.common;

import java.util.*;

public class Graph {
    private final List<List<Edge>> adj;
    private final int n;

    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u, int v, double w) {
        adj.get(u).add(new Edge(u, v, w));
    }

    public int size() {
        return n;
    }

    public List<List<Edge>> adj() {
        return adj;
    }

    public List<Edge> neighbors(int u) {
        return adj.get(u);
    }

    // Построение конденсационного графа
    public static Graph condensation(Graph g, int[] compId, int compCount) {
        Graph dag = new Graph(compCount);
        boolean[][] added = new boolean[compCount][compCount];

        for (int u = 0; u < g.size(); u++) {
            int cu = compId[u];
            for (Edge e : g.neighbors(u)) {
                int cv = compId[e.to()];
                if (cu != cv && !added[cu][cv]) {
                    dag.addEdge(cu, cv, e.w());
                    added[cu][cv] = true;
                }
            }
        }
        return dag;
    }
}