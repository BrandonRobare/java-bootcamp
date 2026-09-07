# Lab 6 - Stream operations table

Project: examples/Lab6-EmployeeAnalytics/. Core path menus 1-9 complete 2026-09-07.

| Operation / API | Used? | Where (method / menu) | Notes |
| --------------- | :---: | --------------------- | ----- |
| Lambda forEach | Yes | displayGroupedEmployees (2) | list.forEach(e -> println("   " + e.getName())), a lambda because it adds indentation |
| Predicate | Yes | getTopPerformers (4), displayPartitionedEmployees (3) | e -> e.getRating() >= min, e -> e.getSalary() > 100_000 |
| Function | Yes | groupingBy(Employee::getDepartment) (2, 6), map(Employee::getSalary) (3) | classifier and projection |
| Consumer | Yes | every forEach, ifPresent (8), ifPresentOrElse first arg (5) | System.out::println is a Consumer |
| Supplier | Yes | TreeMap::new inside groupingBy (2, 6) | the map factory in the 3-arg form |
| filter | Yes | displayActiveEmployees (7), getTopPerformers (4), dashboard active count (8) | |
| map | Yes | displayReductions (3), dashboard department count (8), Optional.map (8) | Optional.map(Map.Entry::getKey) is the same idea over a 0-or-1 box |
| sorted | Yes | getTopSalaries (8), getTopPerformers (4) | both need .reversed(); no-arg sorted() throws, Employee is not Comparable |
| distinct | Yes | dashboard department count (8) | on Stream<String> after map(getDepartment) |
| limit / skip | limit yes, skip no | getTopSalaries(5) (8) | |
| count | Yes | dashboard departments and active (8) | returns primitive long |
| reduce | Yes | displayReductions (3) | reduce(Double::max) and Double::min, needs the boxed Stream<Double> |
| collect(toList/toSet) | toList yes, toSet no | Collectors.toList() as downstream (2); .toList() (4, 8) | Java 16 .toList() where no downstream is needed |
| groupingBy | Yes | (2), getDepartmentStatistics (6), findDepartmentWithHighestAverageSalary (8) | 1-arg, 3-arg with TreeMap::new, and with a downstream collector |
| partitioningBy | Yes | displayPartitionedEmployees (3) | Map<Boolean, List<Employee>>, both keys always present |
| summarizingDouble | Yes | displaySummaryStatistics (3), getDepartmentStatistics (6), dashboard (8) | one pass, five numbers |
| Optional (max / ifPresent) | Yes | displayHighestPaidEmployeeOptional (5), findTopPerformer (8) | ifPresentOrElse takes a Consumer and a Runnable |
| Method references | Yes | throughout | Employee::getSalary, System.out::println, TreeMap::new, Map.Entry::getKey, Double::max |
| Dashboard composed report | Yes | menu 8 | Average Salary : 100680 |

Only skip and collect(toSet) went unused.

## Used but not on the checklist

| Operation | Where | Notes |
| --------- | ----- | ----- |
| mapToDouble | displayReductions (3) | gives a DoubleStream, the only place sum() and average() exist |
| average().orElse(0) | displayReductions (3) | returns OptionalDouble, the primitive cousin of Optional<Double> |
| averagingDouble | findDepartmentWithHighestAverageSalary (8) | downstream collector returning Double |
| Comparator.comparingDouble / comparingInt | (4, 5, 8) | primitive key extractors, no boxing |
| thenComparingDouble | findTopPerformer (8) | rating first, salary as the tiebreak |
| Map.Entry.comparingByValue() | findDepartmentWithHighestAverageSalary (8) | comparator over map entries |
| Map.forEach | (2), displayDepartmentStatistics (6) | takes a BiConsumer, so two parameters instead of one |
