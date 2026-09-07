# Lab 6 - Employee Analytics System

Module 6: streams, lambdas, functional interfaces. JDK 21 on macOS, no build tool.
Core path menus 1-9 complete 2026-09-07.

## Overview

A menu-driven console report over 25 hard-coded employees. EmployeeService owns the data
and the stream pipelines, ReportService composes them into printed reports, and Main is
the switch-based menu loop. Menus 10-21 are labeled bonus stubs and are left unimplemented
on purpose.

| File | Role |
| ---- | ---- |
| Employee | Fields, getters, toString |
| EmployeeData | The 25-row sample set |
| EmployeeService | Stream pipelines: grouping, reductions, partitioning, lookups |
| ReportService | Composes service calls into printed reports |
| Main | Menu loop |

## Compile and run

```bash
cd ~/java-bootcamp/examples/Lab6-EmployeeAnalytics
rm -rf out && javac -d out src/com/academy/analytics/*.java && java -cp out com.academy.analytics.Main
```

## Streams

Every pipeline is a source, some intermediate operations, then exactly one terminal
operation. The terminal operation is picked from the type the caller needs, and that choice
decides everything upstream of it.

| Need | Terminal operation | Track |
| ---- | ------------------ | ----- |
| Optional<T> | max, min, reduce | object |
| List<T> | .toList() | object |
| Map<K,V> | .collect(groupingBy / partitioningBy) | object |
| long | .count() | either |
| double | .sum(), .average().orElse(0) | primitive |
| DoubleSummaryStatistics | .collect(summarizingDouble(...)) | object |

map produces a boxed Stream<Double>, which is where reduce and the collectors live.
mapToDouble produces a DoubleStream, the only place sum() and average() exist.
displayReductions runs both tracks side by side for that reason.

## Functional interfaces

A lambda has no type of its own. It takes the type of the parameter it is passed to, and
that parameter is always an interface with exactly one abstract method.

| Stream API | Interface | Used at |
| ---------- | --------- | ------- |
| filter, partitioningBy | Predicate<T> | menus 3, 4, 7 |
| map, groupingBy | Function<T,R> | menus 2, 3, 6 |
| mapToDouble, summarizingDouble | ToDoubleFunction<T> | menus 3, 6 |
| List.forEach, ifPresent | Consumer<T> | menus 1, 2, 5, 7, 8 |
| Map.forEach | BiConsumer<K,V> | menus 2, 6 |
| reduce | BinaryOperator<T> | menu 3 |
| sorted, max | Comparator<T> | menus 4, 5, 8 |
| groupingBy map factory | Supplier<Map<..>> | menus 2, 6 |
| ifPresentOrElse second arg | Runnable | menu 5 |

Collector is the exception. It has five abstract methods, so it cannot be written as a
lambda, and that is why .collect takes a prebuilt object from the Collectors factory class.

## Lambdas and method references

A method reference replaces a lambda only when the body is pure delegation, so
e -> e.getSalary() collapses to Employee::getSalary. Anything that adds a prefix, a
comparison or formatting stays a lambda: e -> e.getSalary() > 100_000, and
e -> System.out.println("   " + e.getName()).

System.out::println is a Consumer and prints. Employee::getName is a Function and only
fetches, so passing it to forEach compiles, discards 25 strings and prints nothing.

## Sample dashboard, menu 8

```
Employees : 25
Average Salary : 100680
Highest Salary : 165000
Lowest Salary : 48000
Departments : 5
Top Performer : John Smith (Rating 5)
Highest Paid Department : IT
Top 5 Highest Salaries
1 John Smith - 165000
2 Alice Johnson - 152000
3 David Lee - 149000
4 Sarah Brown - 141000
5 Michael Chen - 138000
Active Employees : 23
Inactive Employees : 2
```

## Learnings

Silent no-ops caused more trouble than wrong operations. A collect line with no print after
it compiles and does nothing, an empty method body looks implemented, and
forEach(Employee::getName) type-checks because a method reference that returns a value is
allowed where void is expected, with the value discarded. Writing the println first and
filling in the pipeline above it avoids all three.

sorted() with no argument is a runtime bomb. It means natural order and casts to Comparable,
which Employee does not implement, so it compiles cleanly and throws ClassCastException on
execution. Always pass a comparator.

Sorting is ascending by default, so getTopSalaries and getTopPerformers both need
.reversed().

groupingBy returns a HashMap, so key order is arbitrary. The three-argument form with
TreeMap::new is what makes department output alphabetical in menus 2 and 6.

comparingDouble and comparingInt should be used instead of comparing when the key is a
primitive, since comparing boxes every value. getTopSalaries still uses comparing and could
be tightened.

.sum() is never Optional because an empty sum is 0. .average() is, because the average of
nothing is undefined, and it returns OptionalDouble whose orElse takes a primitive.

## Not implemented

Menus 10-21, the labeled bonuses. The starter README marks them optional and says to finish
the core path first.
