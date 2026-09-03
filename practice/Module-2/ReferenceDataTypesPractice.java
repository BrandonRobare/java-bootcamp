import java.util.ArrayList;
import java.util.List;

public class ReferenceDataTypesPractice {

    // A user-defined class — the objects our references will point to
    static class Employee {
        String name;
    }

    public static void main(String[] args) {
        // A reference holds the object's ADDRESS, not the object itself
        String name = "Anita";              // name refers to a String object
        Employee emp = new Employee();      // emp refers to an Employee object
        emp.name = "Anita";
        System.out.println("emp.name: " + emp.name);

        // A reference can be reassigned
        emp = null;                         // emp no longer points to any object
        System.out.println("emp is null? " + (emp == null));
        // the Employee object now has no references -> eligible for Garbage Collection

        // Best practice: check for null before use
        if (emp != null) {
            System.out.println(emp.name);   // skipped — would be a NullPointerException
        } else {
            System.out.println("null check saved us from a NullPointerException");
        }

        // Arrays and Strings are reference types too
        int[] scores = {90, 85, 77};
        System.out.println("array length: " + scores.length + ", first: " + scores[0]);
        System.out.println("string: " + name);

        // Best practice: prefer the interface (List) over the implementation (ArrayList)
        List<String> team = new ArrayList<>();
        team.add("Anita");
        System.out.println("list: " + team);
    }
}
