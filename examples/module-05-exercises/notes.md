# Module 5 notes

## Exercise 2 - Working with HashSet

Sets determine duplicates using `equals` and `hashCode`. Strings already implement
them. Lab 5 must define identity carefully when custom objects are stored in sets.

Deterministic:
- the second `add("Java")` returns false;
- size stays 3;
- the TreeSet view prints alphabetically.

Raw HashSet iteration order is not a contract and may vary by JDK or run.
Use LinkedHashSet only when insertion order is actually required.

Predict the output: add("X"); add("X"); size is 1.

Output:
Added Java first time: true
Added Java second time: false
Unique count: 3
Contains Testing: true
Sorted view: [Databases, Java, Testing]

## Exercise 3 - Working with HashMap

A Map stores key-value pairs, not values alone. Keys are unique; values need not be.
`put` with an existing key replaces that key's value instead of adding a second entry.
Lab 5 maps book IDs to members rather than keeping two parallel Lists in sync.

Deterministic:
- `put("ISBN-JAVA", 5)` over an existing key changes the value, size does not grow;
- `getOrDefault(key, 0)` returns 0 for an absent key where `get` returns null;
- the TreeMap snapshot prints keys alphabetically.

`get` on a missing key returns null, which throws NPE when unboxed into an int.
Use `getOrDefault` or `containsKey` for lookups that may miss.

Predict the output: put the same key twice; the second value wins and size is unchanged.

Output:
Java copies: 3
Updated Java copies: 5
Missing ISBN: 0
ISBN-TEST -> 4
ISBN-JAVA -> 5
Sorted snapshot: {ISBN-JAVA=5, ISBN-TEST=4}

## Exercise 4 - Sorted Collections: TreeMap

TreeMap keys always iterate in sorted order - that part is guaranteed. HashMap key
order is not a contract; it may differ between runs or JDK versions, so never depend
on it for display order. Sorting is a property of the class, not an operation: there
is no method that sorts a HashMap in place, so build a new TreeMap from it.

Declare the variable as `TreeMap`, not `Map`, when `firstKey`/`lastKey` are needed -
those methods are not on the Map interface.

Deterministic:
- the TreeMap line prints alphabetically;
- `firstKey()` is Annihilation and `lastKey()` is The Hobbit.

With only three short keys the HashMap line can come out alphabetical by coincidence.
That is not evidence of ordering - trust the guarantee, not one run's output.

Predict the output: put B, A, C; `firstKey()` is A.

Output:
HashMap order: [Annihilation, Dune, The Hobbit]
TreeMap order: [Annihilation, Dune, The Hobbit]
First title: Annihilation
Last title: The Hobbit

## Exercise 5 - Safe Removal During Iteration

An Iterator is a cursor over the list, not a copy of it. `iterator.remove()` deletes
the element the latest `next()` returned and fixes the cursor's own position in the
same step, so the walk stays valid. Calling `titles.remove(title)` inside the loop
changes the list behind the cursor's back.

ArrayList keeps a modCount that ticks on every structural change. The iterator records
that number when created and rechecks it on every `next()`. A mismatch throws
ConcurrentModificationException - fail-fast, so a bug crashes instead of corrupting
quietly. Without the check, removal shifts later elements left and the loop skips one.

Deterministic:
- both Deprecated titles are removed;
- `Remaining: [Java 21, Clean Code]`.

Fail-fast is a tripwire, not a guarantee. Removing the second-to-last element leaves
the cursor position equal to the new size, so `hasNext()` returns false, the loop exits
early and no exception is ever thrown. That is why the list-side remove is called
unsafe rather than just "throws" - sometimes it is silently wrong instead.

Order is strict: `hasNext()` then `next()` then at most one `remove()` per `next()`.
Removing before any `next()` throws IllegalStateException.

`titles.removeIf(title -> title.startsWith("Deprecated"))` does the same job in one
line and is the right choice when filtering by a condition. Lab 5 uses the Iterator
because the safe-removal contract has to be understood, not just avoided.

Predict the output: `list.remove` inside a for-each throws ConcurrentModificationException.

Output:
Remaining: [Java 21, Clean Code]
