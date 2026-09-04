public class LambdaDemo {
    public static void main(String[] args) {
        Employee alice = EmployeeData.sample().get(0);

        // DONE: anonymous class implementing SalaryCheck — salary > 60_000
        SalaryCheck anonymous = new SalaryCheck() {
            @Override
            public boolean test(Employee employee) {
                return employee.salary() > 60_000;
            }
        };

        // DONE: lambda with the same SalaryCheck contract and same result
        SalaryCheck lambda = employee -> {return employee.salary() > 60_000; };

        System.out.println("Employee: " + alice.name());
        System.out.println("Anonymous result: " + anonymous.test(alice));
        System.out.println("Lambda result: " + lambda.test(alice));
    }
}
