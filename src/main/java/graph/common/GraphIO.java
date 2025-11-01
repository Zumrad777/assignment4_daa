package graph.common;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.*;

public class GraphIO {
    public static Graph load(String path) throws IOException {
        String text = Files.readString(Path.of(path));
        JSONObject obj = new JSONObject(text);
        int n = obj.getInt("n");
        Graph g = new Graph(n);
        JSONArray edges = obj.getJSONArray("edges");
        for (int i = 0; i < edges.length(); i++) {
            JSONObject e = edges.getJSONObject(i);
            int u = e.getInt("u");
            int v = e.getInt("v");
            double w = e.getDouble("w");
            g.addEdge(u, v, w);
        }
        return g;
    }
}
