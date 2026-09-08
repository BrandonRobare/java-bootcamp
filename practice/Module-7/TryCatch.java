public class TryCatch {
    public static void main(String[] args) {
        try{
            int result = 10 / 0; //ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero: " + e.getMessage());
        } finally {
            System.out.println("Execution completed");
        }
    }


}
