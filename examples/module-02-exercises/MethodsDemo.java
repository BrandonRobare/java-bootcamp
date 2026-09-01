public class MethodsDemo {
    // DONE: method that takes an int and returns n * n
    public static int square(int n) {
        return n * n;
    }

    // DONE: overload — same name, double parameter, return n * n as double
    public static double square(double n) {
         return n * n;
    }

    public static void main(String[] args) {
        // DONE: call both overloads and print the results
         int intResult = square(4);
         double doubleResult = square(2.5);
         System.out.println("square(4) = " + intResult);
         System.out.println("square(2.5) = " + doubleResult);
    }
}
