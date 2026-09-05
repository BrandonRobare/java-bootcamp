import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepartmentCountDemo {
    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.sample();

        // DONE: groupingBy department + counting
        Map<String, Long> counts = employees.stream()
                // DONE: .collect(Collectors.groupingBy(...))
                .collect(Collectors.groupingBy(Employee::department, Collectors.counting()));

        Map<String, Long> sortedCounts = new TreeMap<>(counts);

        sortedCounts.forEach((dept, count) ->
                System.out.println(dept + ": " + count));
    }

}
