public class FinallyDemo {
    public static void main(String[] args) {
        // DONE: success path — try/catch/finally printing stage labels
        try {
            System.out.println("try: success work");
        } catch (Exception ex) {
            System.out.println("catch: " + ex.getClass().getSimpleName());
        } finally {
            // DONE
            System.out.println("finally: success");
        }

        // DONE: failure path — throw/catch and prove finally still runs
        try {
            int x = 10 / 0;
            System.out.println(x);
        } catch (ArithmeticException ex) {
            System.out.println("catch: " + ex.getClass().getSimpleName());
        } finally {
            System.out.println("finally: failure");
        }
    }
}
