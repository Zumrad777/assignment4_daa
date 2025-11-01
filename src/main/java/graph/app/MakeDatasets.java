package graph.app;

import graph.common.DataGen;

public class MakeDatasets {
    public static void main(String[] args) throws Exception {
        // Small
        DataGen.makeDAG("data/small_dag_1.json", 8, 0.25, 1);
        DataGen.makeCyclic("data/small_cyc_2.json", 9, 0.20, 2, 2);
        DataGen.makeDAG("data/small_dag_3.json", 10, 0.30, 3);

        // Medium
        DataGen.makeCyclic("data/med_mix_1.json", 15, 0.25, 4, 4);
        DataGen.makeDAG("data/med_dag_2.json", 18, 0.25, 5);
        DataGen.makeCyclic("data/med_mix_3.json", 20, 0.30, 6, 6);

        // Large
        DataGen.makeDAG("data/large_dag_1.json", 30, 0.15, 7);
        DataGen.makeCyclic("data/large_mix_2.json", 40, 0.12, 12, 8);
        DataGen.makeCyclic("data/large_mix_3.json", 50, 0.10, 15, 9);

        System.out.println(" 9 datasets generated in /data/");
    }
}
