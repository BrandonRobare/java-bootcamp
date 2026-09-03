# Lab 4: Memory Management and Garbage Collection

## GC log snippet (`java -Xlog:gc GarbageCollectionDemo`, -Xms16m -Xmx64m)

```
[0.003s][info][gc] Using G1
[0.034s][info][gc] GC(0) Pause Young (Normal) (G1 Evacuation Pause) 9M->8M(18M) 1.379ms
[0.042s][info][gc] GC(7) Pause Young (Prepare Mixed) (G1 Evacuation Pause) 24M->24M(43M) 0.992ms
Objects Created : 100000
[0.044s][info][gc] GC(8) Pause Full (System.gc()) 25M->1M(16M) 1.109ms
```

Used memory rose from 1 MB (Before Allocation) to 25 MB (After Allocation), then
dropped back to 1 MB (After GC) once the array reference was nulled and GC ran.

## Performance table (`java -Xms128m -Xmx512m PerformanceTest`)

| Objects | Used Memory (approx) | Execution Time |
| ------- | --------------------- | --------------- |
| 10 | 0.00 MB | 0 ms |
| 100 | 0.46 MB | 0 ms |
| 1,000 | 0.46 MB | 0 ms |
| 100,000 | 10.46 MB | 7 ms |
| 1,000,000 | 107.78 MB | 45 ms |

## Leak vs fix

`MemoryLeakDemo leak` keeps adding `Employee` objects into a static field
(`LEAK_HOLDER.employees`). A static field is a GC root, so every object added
to that list stays reachable forever: memory climbed steadily from 2 MB to
385 MB across all 1,000,000 adds and never dropped, because nothing ever
stopped pointing at those objects. `MemoryLeakDemo fix` does the same kind of
allocation (500,000 employees) but into a local `ArrayList` instead. Once the
list is `clear()`ed and the local reference is set to `null`, no GC root can
reach those objects anymore, so `System.gc()` actually reclaims them: memory
dropped from 188 MB back down to 1 MB. The bug in `leak` isn't a broken
collector; it's a retained reference the collector is correctly obeying.

## Reflection questions

**1. Stack vs Heap?**
The stack holds each thread's per-method frames: primitives and references,
created and destroyed as methods are called and return. The heap is one
shared space holding the actual objects those references point to, and it
lives independently of any single method call.

**2. Why locals on the Stack?**
A local variable (a primitive or a reference) only needs to exist for the
lifetime of the method call that declared it, so it's cheap to store in a
frame that's popped automatically when the method returns.

**3. Why objects on the Heap?**
Objects often need to outlive the method that created them: they can be
returned, stored in a field, or shared across multiple method calls/threads,
so they're allocated in a shared space that isn't tied to any one stack frame,
and reclaimed only once nothing references them anymore.
