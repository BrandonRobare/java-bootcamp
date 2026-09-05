import java.util.List;

public class HrNamesDemo {
    public static void main(String[] args) {
        List<Employee> employees = EmployeeData.sample();

        // DONE: filter HR → map name → sorted → toList
        List<String> hrNames = employees.stream()
                // DONE: .filter(...)
                .filter(e -> e.department().equals("HR"))
                // DONE: .map(...)
                .map(Employee::name) // also can use .map(e -> e.name) instead of method reference
                // DONE: .sorted()
                .sorted()
                // DONE: .toList()
                .toList();

        System.out.println("HR names:");
        hrNames.forEach(System.out::println);
    }
}
