# Module 4 notes

## Exercise 4 — Select and Verify G1

Command:
java -XX:+UseG1GC -Xms16m -Xmx64m -Xlog:gc GcObserve

Evidence:
The log began with "Using G1" and showed G1 evacuation pauses.
The collector flag selects G1; it does not guarantee a particular pause time.


[0.005s][info][gc] Using G1
[0.030s][info][gc] GC(0) Pause Young (Normal) (G1 Evacuation Pause) 8M->8M(18M) 2.053ms
[0.031s][info][gc] GC(1) Pause Young (Concurrent Start) (G1 Evacuation Pause) 9M->9M(18M) 0.934ms
[0.031s][info][gc] GC(2) Concurrent Mark Cycle
[0.032s][info][gc] GC(3) Pause Young (Normal) (G1 Evacuation Pause) 10M->10M(18M) 0.565ms
[0.033s][info][gc] GC(4) Pause Young (Normal) (G1 Evacuation Pause) 12M->12M(18M) 0.626ms
[0.034s][info][gc] GC(5) Pause Young (Normal) (G1 Evacuation Pause) 15M->15M(39M) 0.748ms
[0.035s][info][gc] GC(2) Pause Remark 16M->16M(39M) 0.648ms
[0.036s][info][gc] GC(2) Pause Cleanup 21M->21M(39M) 0.099ms
[0.037s][info][gc] GC(6) Pause Young (Prepare Mixed) (G1 Evacuation Pause) 21M->21M(39M) 0.971ms
[0.037s][info][gc] GC(2) Concurrent Mark Cycle 5.732ms
[0.038s][info][gc] GC(7) Pause Young (Mixed) (G1 Evacuation Pause) 27M->27M(39M) 0.684ms
[0.039s][info][gc] GC(8) Pause Young (Concurrent Start) (G1 Evacuation Pause) 35M->35M(45M) 0.969ms
[0.039s][info][gc] GC(9) Concurrent Mark Cycle
[0.039s][info][gc] GC(10) Pause Young (Normal) (G1 Evacuation Pause) 41M->35M(53M) 0.270ms
[0.040s][info][gc] GC(11) Pause Young (Normal) (G1 Evacuation Pause) 48M->34M(53M) 0.131ms
[0.042s][info][gc] GC(12) Pause Young (Normal) (G1 Evacuation Pause) 47M->47M(62M) 1.251ms
[0.042s][info][gc] GC(9) Pause Remark 48M->25M(62M) 0.069ms
[0.043s][info][gc] GC(9) Pause Cleanup 25M->25M(62M) 0.023ms
[0.043s][info][gc] GC(9) Concurrent Mark Cycle 3.994ms
Completed round 5
[0.049s][info][gc] GC(13) Pause Young (Prepare Mixed) (G1 Evacuation Pause) 34M->32M(62M) 0.574ms
[0.051s][info][gc] GC(14) Pause Young (Mixed) (G1 Evacuation Pause) 55M->35M(62M) 0.592ms
[0.051s][info][gc] GC(15) Pause Young (Concurrent Start) (G1 Evacuation Pause) 57M->32M(63M) 0.196ms
[0.051s][info][gc] GC(16) Concurrent Mark Cycle
Completed round 10
[0.052s][info][gc] GC(17) Pause Young (Normal) (G1 Evacuation Pause) (Evacuation Failure) 57M->47M(64M) 0.420ms
[0.053s][info][gc] GC(18) Pause Young (Normal) (G1 Evacuation Pause) (Evacuation Failure) 59M->57M(64M) 0.311ms
[0.053s][info][gc] GC(19) Pause Young (Normal) (G1 Evacuation Pause) 60M->57M(64M) 0.192ms
[0.054s][info][gc] GC(20) Pause Young (Normal) (G1 Evacuation Pause) (Evacuation Failure) 59M->60M(64M) 0.264ms
[0.054s][info][gc] GC(21) Pause Young (Normal) (G1 Evacuation Pause) (Evacuation Failure) 63M->63M(64M) 0.115ms
[0.056s][info][gc] GC(22) Pause Full (G1 Compaction Pause) 63M->8M(37M) 2.000ms
[0.056s][info][gc] GC(16) Concurrent Mark Cycle 4.641ms
[0.057s][info][gc] GC(23) Pause Young (Normal) (G1 Evacuation Pause) 18M->18M(37M) 0.710ms
[0.058s][info][gc] GC(24) Pause Young (Concurrent Start) (G1 Evacuation Pause) 31M->21M(39M) 0.528ms
[0.058s][info][gc] GC(25) Concurrent Mark Cycle
Completed round 15
[0.059s][info][gc] GC(26) Pause Young (Normal) (G1 Evacuation Pause) 33M->22M(40M) 0.208ms
[0.059s][info][gc] GC(27) Pause Young (Normal) (G1 Evacuation Pause) 34M->22M(50M) 0.117ms
[0.060s][info][gc] GC(25) Pause Remark 31M->18M(34M) 0.146ms
[0.061s][info][gc] GC(28) Pause Young (Normal) (G1 Evacuation Pause) 29M->16M(41M) 0.688ms
[0.061s][info][gc] GC(25) Pause Cleanup 16M->16M(41M) 0.018ms
[0.061s][info][gc] GC(25) Concurrent Mark Cycle 2.934ms
[0.061s][info][gc] GC(29) Pause Young (Prepare Mixed) (G1 Evacuation Pause) 36M->16M(41M) 0.155ms
Completed round 20
Allocated bytes over time: 262144000