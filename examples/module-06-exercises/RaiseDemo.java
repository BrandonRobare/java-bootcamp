import java.util.List;

public class RaiseDemo {
    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.sample();

        // Done: map each salary * 1.10 without mutating source employees
        List<Double> raised = employees.stream()
                // DONE: .map(...)
                .map(employee -> employee.salary() * 1.10)
                // DONE: .toList()
                .toList();

        System.out.println("Raised salaries:");
        raised.forEach(s -> System.out.printf("%.0f%n", s));

        System.out.println("Original Alice salary: " + employees.get(0).salary());
    }
}
