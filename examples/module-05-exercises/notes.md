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
