public class CommonExceptionsDemo {
    public static void main(String[] args) {
        try {
            int divisor = 0;
            int result = 10 / divisor;
            System.out.println(result);
        } catch (ArithmeticException ex) { // DONE: ArithmeticException
            System.out.println("Caught: " + ex.getClass().getSimpleName());
        }

        try {
            String value = null;
            System.out.println(value.length());
        } catch (NullPointerException ex) { // DONE: NullPointerException
            System.out.println("Caught: " + ex.getClass().getSimpleName());
        }

        try {
            int[] values = {10, 20};
            System.out.println(values[5]);
        } catch (ArrayIndexOutOfBoundsException ex) { // DONE: ArrayIndexOutOfBoundsException
            System.out.println("Caught: " + ex.getClass().getSimpleName());
        }

        System.out.println("Program continued.");
    }
}
