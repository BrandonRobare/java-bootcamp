# Module 4 notes

## Exercise 4 - Select and Verify G1

Command:
java -XX:+UseG1GC -Xms16m -Xmx64m -Xlog:gc GcObserve

Evidence:
The log began with "Using G1" and showed G1 evacuation pauses.
The collector flag selects G1; it does not guarantee a particular pause time.

Excerpt:
[0.005s][info][gc] Using G1
[0.030s][info][gc] GC(0) Pause Young (Normal) (G1 Evacuation Pause) 8M->8M(18M) 2.053ms
[0.052s][info][gc] GC(17) Pause Young (Normal) (G1 Evacuation Pause) (Evacuation Failure) 57M->47M(64M) 0.420ms
[0.056s][info][gc] GC(22) Pause Full (G1 Compaction Pause) 63M->8M(37M) 2.000ms
Completed round 20
Allocated bytes over time: 262144000

## Exercise 5 - Select and Verify ZGC

Command:
java -XX:+UseZGC -Xms16m -Xmx64m -Xlog:gc GcObserve

Evidence:
The log began with "Using The Z Garbage Collector" instead of "Using G1".
Pause-related log lines look different — ZGC does most of its work concurrently,
so it does not report the same kind of stop-the-world "Evacuation Pause" G1 does.
Instead it logs "Allocation Stall" / "Garbage Collection" entries, and far fewer
of them than G1 logged for the same workload.

Output:
[0.007s][info][gc] Using The Z Garbage Collector
[0.038s][info][gc] Allocation Stall (main) 2.836ms
[0.038s][info][gc] GC(0) Garbage Collection (Allocation Stall) 64M(100%)->16M(25%)
Completed round 5
[0.046s][info][gc] Allocation Stall (main) 1.817ms
[0.047s][info][gc] GC(1) Garbage Collection (Allocation Stall) 64M(100%)->28M(44%)
Completed round 10
[0.049s][info][gc] Allocation Stall (main) 1.556ms
[0.050s][info][gc] GC(2) Garbage Collection (Allocation Stall) 64M(100%)->36M(56%)
Completed round 15
[0.053s][info][gc] Allocation Stall (main) 2.366ms
[0.053s][info][gc] GC(3) Garbage Collection (Allocation Stall) 64M(100%)->32M(50%)
Completed round 20
Allocated bytes over time: 262144000

## Exercise 6 - Retained References (Safe Leak Sketch)

Retaining path:
loaded RetentionDemo class
  → static CACHE field
  → ArrayList entries
  → byte[] objects

Root cause: a long-lived static collection retained strong references after
the data was no longer needed. GC could not reclaim reachable entries.

Fix: clear/remove entries, bound the cache, apply eviction, or use a more
appropriate lifecycle. Weak references are not a universal cache fix.

Output:
Before: 2 MB
Retained objects: 10000
After allocation: 12 MB
After clear (approx): 1 MB

## Exercise 7 — String vs StringBuilder

| Run | String ms | StringBuilder ms |
| --- | --------- | ---------------- |
| 1 | 68.452 | 1.033 |
| 2 | 64.115 | 1.041 |
| 3 | 69.320 | 1.006 |

Use StringBuilder when constructing text repeatedly in a loop. Ordinary +
remains readable and appropriate for a small, fixed expression.
