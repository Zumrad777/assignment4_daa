#  Assignment 4 — Smart City / Smart Campus Scheduling

**Course:** Design and Analysis of Algorithms (SE-2401)  
**Student:** Zumrad Sherbadalova  
**Date:** 1 November 2025  

---

##  Goal

Integrate three key algorithmic techniques into one scheduling framework for **Smart City** and **Smart Campus** operations — such as street cleaning, repair scheduling, and sensor maintenance.

### **Algorithms Implemented**
* **Strongly Connected Components (SCC)** — Tarjan’s Algorithm  
* **Topological Sorting** — Kahn’s Algorithm  
* **Shortest and Longest Paths in DAGs** — Dynamic Programming approach  

---

##  Scenario

The input datasets represent interdependent service tasks.  
Some dependencies form **cycles** (detected and compressed using SCC),  
others are **acyclic** (optimized via topological sorting and shortest-path DP).  
Each task has an associated **duration weight (`w`)**, defining time or cost.

This framework models realistic scheduling systems for smart infrastructures.

---

##  Implementation Overview

| Step | Algorithm | Time Complexity | Key Operations | Output |
| :---: | :--- | :--- | :--- | :--- |
| 1 | **TarjanSCC** | O(V + E) | DFS visits, low-link updates | SCC labels, component count |
| 2 | **Condensation** | O(V + E) | Edge grouping by SCC | DAG of components |
| 3 | **KahnTopo** | O(V + E) | Queue push/pop | Topological order |
| 4 | **DagShortestPaths** | O(V + E) | Relaxations | Shortest distances |
| 5 | **DagLongestPath** | O(V + E) | Updates (max-DP) | Critical path |

**Metrics tracked:** `dfsVisits`, `edges`, `pushes`, `pops`, `relaxations`, `execution time (ms)`

---

##  Data Summary

| Dataset | Nodes (n) | Edges (|E|) | Structure | Type | Cyclic |
| :--- | :---: | :---: | :--- | :--- | :---: |
| **tasks.json** | 8 | 7 | Two clusters (3-cycle + linear chain) | Mixed |  Yes |

 Located in: `/data/tasks.json` (used by `App.java`)

---

##  Results

| Algorithm | Time (ms) | dfsVisits | edges | pushes | pops | relaxations | updates |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **SCC (Tarjan)** | 1.0908 | 8 | 7 | – | – | – | – |
| **Condensation** | 0.0273 | – | – | – | – | – | – |
| **Topological Sort (Kahn)** | 0.5148 | – | – | 6 | 6 | – | – |
| **Shortest Paths (DAG-SP)** | 0.6210 | – | – | – | – | 0 | – |
| **Longest Path (Critical)** | 0.8491 | – | – | – | – | – | 4 |

**Critical Path Length:** 8.0  
**Critical Path:** `[5, 4, 3, 2]`  
**Total SCCs:** 6  
**Condensation Vertices:** 6  

---

##  Analysis

###  Strongly Connected Components (SCC)
* Detected **6 components**, including a 3-cycle `[1, 2, 3]`.
* Complexity ≈ O(V + E) ≈ O(15); runtime ≈ **1 ms**.
* Main cost — recursive DFS, efficient for medium-dense graphs.

###  Condensation & Topological Order
* Condensation built a **6-node DAG** in **0.03 ms**.  
* Topological sort completed in **0.5 ms** (6 queue ops).  
* Order: `[1, 5, 0, 4, 3, 2]` — valid execution sequence.

###  Shortest Paths (DAG-SP)
* No reachable vertices from source 0 — correct for disconnected DAG.  
* Performed **0 relaxations**, confirming logical correctness.

###  Longest Path (Critical Chain)
* Found critical path `[5, 4, 3, 2]` with length **8.0** in **0.85 ms**.  
* Represents **maximum workflow time** — crucial for scheduling optimization.

---

##  Conclusions

| Situation | Recommended Approach |
| :--- | :--- |
| Graph contains cycles | Tarjan SCC → Condensation → Topological Sort |
| Pure DAG scheduling | Kahn Topo + Shortest/Longest Path |
| Detect dependencies | SCC Analysis |
| Minimize completion time | DAG Shortest Path |
| Find bottlenecks | DAG Longest Path (Critical Chain) |

 **Combining SCC, Topological Sort, and DAG-SP** creates a full scheduling framework for smart systems.

---

##  Testing

JUnit 5 tests implemented and passed successfully:

| Test Class | Description | Result |
| :--- | :--- | :---: |
| **SccTopoTests** | Verifies SCC & Topological order correctness |  Passed (2/2) |
| **DagSpTests** | Validates Shortest & Longest Paths logic |  Passed (2/2) |

