package graph.common;
public class Metrics {

    private long startTime;
    private long endTime;
    private int operations;


    public long dfsVisits = 0;      // for Tarjan/Kosaraju
    public long dfsEdgeScans = 0;   // for SCC edge traversals
    public long kahnPushes = 0;     // queue additions in Kahn
    public long kahnPops = 0;       // queue removals in Kahn
    public long relaxations = 0;    // edge relaxations in DAG shortest paths
    public void start() {
        startTime = System.nanoTime();
        operations = 0;
    }
    public void stop() {
        endTime = System.nanoTime();
    }

    public void tick() {
        operations++;
    }


    public double getTimeMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    public int getOps() {
        return operations;
    }

    public void resetCounters() {
        dfsVisits = dfsEdgeScans = kahnPushes = kahnPops = relaxations = 0;
        operations = 0;
    }
}
