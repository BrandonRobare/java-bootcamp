package com.academy.analytics;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {

    private final List<Employee> employees;

    public EmployeeService(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    // --- CORE (menus 1–9) — keep throwing until implemented ---

    public void displayAllEmployees() {
        System.out.println("Total Employees : " + employees.size());
        System.out.println("Employee List");
        // DONE (menu 1): stream forEach print each employee
        employees.stream().forEach(System.out::println); // .forEach(e -> System.out.println(e)) keep lambda when it does something other than delegate
    }

    public void displayActiveEmployees() {
        System.out.println("Active Employees:");
        // DONE (menu 7): filter Employee::isActive; forEach
        employees.stream().filter(Employee::isActive).forEach(System.out::println);
    }

    public void displayGroupedEmployees() {
        // DONE (menu 2): groupingBy department into TreeMap; print each group
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, TreeMap::new, Collectors.toList()));

        byDept.forEach((dept, list) -> {
            System.out.println(dept + " (" + list.size() + ")");
            list.forEach(e -> System.out.println("   " + e.getName()));
        });
    }

    public void displayReductions() {
        // DONE (menu 3): highest/lowest via reduce; total/average via mapToDouble
        Optional<Double> highest = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::max);

        Optional<Double> lowest = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::min);

        double total = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();

        double average = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average().orElse(0);

        System.out.println("Highest Salary : " + highest.orElse(0.0));
        System.out.println("Lowest Salary : " + lowest.orElse(0.0));
        System.out.printf("Total Salary : %.0f%n", total);
        System.out.printf("Average Salary : %.0f%n", average);
    }

    public void displaySummaryStatistics() {
        // DONE (menu 3): summarizingDouble salary; print max/min/avg/sum/count
        DoubleSummaryStatistics stats = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        System.out.println("max: " + stats.getMax());
        System.out.println("min: " + stats.getMin());
        System.out.println("avg: " + stats.getAverage());
        System.out.println("sum: " + stats.getSum());
        System.out.println("count: " + stats.getCount());
    }

    public void displayPartitionedEmployees() {
        // DONE (menu 3): partitioningBy salary > 100_000
        Map<Boolean, List<Employee>> partitionedEmployees = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 100_000));

        System.out.println("High Earners (> 100000):");
        partitionedEmployees.get(true).forEach(System.out::println);

        System.out.println("Others:");
        partitionedEmployees.get(false).forEach(System.out::println);
    }

    public void displayHighestPaidEmployeeOptional() {
        // DONE (menu 5): max by salary; ifPresentOrElse
        Optional<Employee> highestPaid = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));

                highestPaid.ifPresentOrElse(e -> System.out.println("Highest Paid Employee: " + e),
                                                    () -> System.out.println("No Employee Found"));
    }

    public Optional<Employee> findTopPerformer() {
        // DONE (menu 8 dashboard): max by rating then salary
        return employees.stream()
                .max(Comparator.comparingInt(Employee::getRating).thenComparingDouble(Employee::getSalary));
    }

    public List<Employee> getTopSalaries(int count) {
        // DONE (menu 8 dashboard): sorted salary desc; limit count; toList
        return employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(count)
                .toList();
    }

    public List<Employee> getTopPerformers(int minimumRating) {
        // DONE (menu 4): filter rating >= minimum; sort; toList
        return employees.stream()
                .filter(e -> e.getRating() >= minimumRating)
                .sorted(Comparator.comparingInt(Employee::getRating).reversed())
                .toList();
    }

    public Map<String, DoubleSummaryStatistics> getDepartmentStatistics() {
        return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        TreeMap::new,
                        Collectors.summarizingDouble(Employee::getSalary)));
    }

    public Optional<String> findDepartmentWithHighestAverageSalary() {
        // DONE (menu 8 dashboard): groupingBy averagingDouble; max entry; map key
         return employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                 .entrySet()
                 .stream()
                 .max(Map.Entry.comparingByValue())
                 .map(Map.Entry::getKey);
    }

    // --- BONUS / DEMO (menus 10–21) — stub so explorers do not crash ---

    public void demonstrateLambdas() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void demonstrateFunctionalInterfaces() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void demonstrateStreamSources() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayHighSalaryEmployees() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayItEmployees() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayFilteredItTopPerformers() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void demonstrateMapping() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void demonstrateSorting() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayDistinctDepartments() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayTopAndNextSalaries() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void displayCounts() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public void demonstrateCollectors() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }

    public Optional<Employee> findHighestPaidEmployee() {
        System.out.println("Bonus / full-path feature — implement after CORE");
        return Optional.empty();
    }

    public Optional<Double> findSecondHighestSalary() {
        System.out.println("Bonus / full-path feature — implement after CORE");
        return Optional.empty();
    }

    public Optional<Employee> findEmployeeWithLongestName() {
        System.out.println("Bonus / full-path feature — implement after CORE");
        return Optional.empty();
    }

    public Map<String, Long> generateSalaryHistogram() {
        System.out.println("Bonus / full-path feature — implement after CORE");
        return Map.of();
    }

    public String collectEmployeeSummary() {
        System.out.println("Bonus / full-path feature — implement after CORE");
        return "";
    }
}
