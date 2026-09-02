public class srpPractice {

    // Bad: one class, two responsibilities
    static class Bad {
        static class Employee {
            private int id;
            private String name;
            private double salary;

            void setEmployee(int id, String name, double salary) {
                this.id = id;
                this.name = name;
                this.salary = salary;
            }

            String generateReport() {
                return "ID:" + id + " Name:" + name;
            }
        }
    }

    // Good: separate responsibilities
    static class Good {
        static class Employee {
            private final int id;
            private final String name;
            private final double salary;

            Employee(int id, String name, double salary) {
                this.id = id;
                this.name = name;
                this.salary = salary;
            }

            int getId() { return id; }
            String getName() { return name; }
            double getSalary() { return salary; }
        }

        static class EmployeeReportGenerator {
            String generateReport(Employee e) {
                return "ID:" + e.getId() + " Name:" + e.getName();
            }
        }
    }

    public static void main(String[] args) {
        Bad.Employee bad = new Bad.Employee();
        bad.setEmployee(1, "Aman", 50000);
        System.out.println("bad:  " + bad.generateReport());

        Good.Employee good = new Good.Employee(1, "Aman", 50000);
        System.out.println("good: " + new Good.EmployeeReportGenerator().generateReport(good));
    }
}
