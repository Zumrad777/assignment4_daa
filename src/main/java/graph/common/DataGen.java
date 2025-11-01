package graph.common;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class DataGen {

    public static void saveGraph(String file, int n, List<int[]> edges, List<Double> w) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("n", n);
        JSONArray arr = new JSONArray();
        for (int i = 0; i < edges.size(); i++) {
            int[] e = edges.get(i);
            JSONObject je = new JSONObject();
            je.put("u", e[0]);
            je.put("v", e[1]);
            je.put("w", w.get(i));
            arr.put(je);
        }
        obj.put("edges", arr);
        Files.writeString(Path.of(file), obj.toString(2));
    }

    public static void makeDAG(String file, int n, double density, long seed) throws Exception {
        Random rnd = new Random(seed);
        List<int[]> E = new ArrayList<>();
        List<Double> W = new ArrayList<>();
        for (int u = 0; u < n; u++) {
            for (int v = u + 1; v < n; v++) {
                if (rnd.nextDouble() < density) {
                    E.add(new int[]{u, v});
                    W.add(1.0 + rnd.nextInt(9));
                }
            }
        }
        saveGraph(file, n, E, W);
    }

    public static void makeCyclic(String file, int n, double density, int backEdges, long seed) throws Exception {
        Random rnd = new Random(seed);
        List<int[]> E = new ArrayList<>();
        List<Double> W = new ArrayList<>();

        for (int u = 0; u < n; u++)
            for (int v = u + 1; v < n; v++)
                if (rnd.nextDouble() < density) {
                    E.add(new int[]{u, v});
                    W.add(1.0 + rnd.nextInt(9));
                }

        for (int i = 0; i < backEdges; i++) {
            int a = 1 + rnd.nextInt(n - 1);
            int b = rnd.nextInt(a);
            E.add(new int[]{a, b});
            W.add(1.0 + rnd.nextInt(9));
        }
        saveGraph(file, n, E, W);
    }
}
