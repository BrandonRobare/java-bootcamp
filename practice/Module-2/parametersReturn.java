public class parametersReturn {
    // parameters and returns
    static int multiply(int a, int b) { // a and b are parameters
        return a * b; //return statement
    }

    // parameters and no return
    static void print(String text) {
        System.out.println(text); //no return
    }

    // returns a reference type
    // Employee has only the default no-arg constructor
    static referenceDataTypesPractice.Employee createEmployee(String name) {
        referenceDataTypesPractice.Employee employee = new referenceDataTypesPractice.Employee();
        employee.name = name;
        return employee;
    }

    public static void main(String[] args) {
        String test = "Testing void return";
        print(test);

        int num1 = 4;
        int num2 = 6;
        System.out.println(multiply(num1, num2));

        referenceDataTypesPractice.Employee emp = createEmployee("Anita");
        System.out.println("createEmployee returned: " + emp.name);
    }
}
