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
