import java.util.List;

public class FilterSalaryDemo {
    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.sample();

        // DONE: filter salary > 60_000, collect to List
        List<Employee> highEarners = employees.stream()
                // DONE: .filter(...)
                .filter(e -> e.salary() > 60_000)
                // DONE: .toList()
                .toList();

        System.out.println("Employees above 60000:");
        highEarners.forEach(employee ->
                System.out.printf("%s - %.0f%n",
                        employee.name(), employee.salary()));

        System.out.println("Source size: " + employees.size());
        System.out.println("Filtered size: " + highEarners.size());
    }
}
