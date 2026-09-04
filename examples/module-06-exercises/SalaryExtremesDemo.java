import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SalaryExtremesDemo {
    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.sample();

        Comparator<Employee> bySalary = Comparator.comparingDouble(Employee::salary);

        Optional<Employee> highest = employees.stream()
                .max(bySalary);

        Optional<Employee> lowest = employees.stream()
                .min(bySalary);

        highest.ifPresent(e -> System.out.printf("Highest: %s - %.0f%n", e.name(), e.salary()));
        lowest.ifPresent(e -> System.out.printf("Lowest: %s - %.0f%n", e.name(), e.salary()));
    }
}
