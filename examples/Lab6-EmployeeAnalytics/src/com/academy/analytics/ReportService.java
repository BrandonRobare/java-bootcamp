package com.academy.analytics;

import java.util.List;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReportService {

    private final EmployeeService employeeService;

    public ReportService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // --- CORE (menus 1–9) — keep throwing until implemented ---

    public void displayDashboard() {
        // DONE (menu 8): stream stats (avg/max/min), department count, active/inactive
        List<Employee> employees = employeeService.getEmployees();

        DoubleSummaryStatistics stats = employees.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));

        long departmentCount = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .count();

        long activeCount = employees.stream()
                .filter(Employee::isActive)
                .count();

        long inactiveCount = employees.size() - activeCount;

        // DONE: top performer, highest-paid dept, top 5 salaries — print dashboard block
        Optional<Employee> topPerformer = employeeService.findTopPerformer();
        Optional<String> highestPaidDepartment = employeeService.findDepartmentWithHighestAverageSalary();
        List<Employee> topSalaries = employeeService.getTopSalaries(5);


        System.out.println("=============================");
        System.out.println("Employee Analytics Dashboard");
        System.out.println("=============================");
        System.out.println("Employees : " + employees.size());
        System.out.printf("Average Salary : %.0f%n", stats.getAverage());
        System.out.printf("Highest Salary : %.0f%n", stats.getMax());
        System.out.printf("Lowest Salary : %.0f%n", stats.getMin());
        System.out.println("Departments : " + departmentCount);

        topPerformer.ifPresent(e ->
                System.out.println("Top Performer : " + e.getName()
                        + " (Rating " + e.getRating() + ")"));
        highestPaidDepartment.ifPresent(d ->
                System.out.println("Highest Paid Department : " + d));

        System.out.println("Top 5 Highest Salaries");
        for (int i = 0; i < topSalaries.size(); i++) {
            Employee e = topSalaries.get(i);
            System.out.printf("%d %s - %.0f%n", i + 1, e.getName(), e.getSalary());
        }

        System.out.println("Active Employees : " + activeCount);
        System.out.println("Inactive Employees : " + inactiveCount);
        // Expected with solution seed: Average Salary : 100680
    }

    public void displayEmployeesByDepartment() {
        employeeService.displayGroupedEmployees();
    }

    public void displaySalaryReport() {
        employeeService.displayReductions();
        System.out.println();
        employeeService.displaySummaryStatistics();
        System.out.println();
        employeeService.displayPartitionedEmployees();
    }

    public void displayTopPerformers() {
        System.out.println("Top Performers (Rating >= 4):");
        // TODO (menu 4): employeeService.getTopPerformers(4).forEach(...)
        throw new UnsupportedOperationException("TODO");
    }

    public void displayHighestSalary() {
        employeeService.displayHighestPaidEmployeeOptional();
    }

    public void displayDepartmentStatistics() {
        // TODO (menu 6): getDepartmentStatistics(); print count/avg/max/min per dept
        throw new UnsupportedOperationException("TODO");
    }

    public void displayActiveEmployees() {
        employeeService.displayActiveEmployees();
    }

    // --- BONUS (menu 21) ---

    public void displayBonusInsights() {
        System.out.println("Bonus / full-path feature — implement after CORE");
    }
}
