public class ControlFlowBug {
    public static void main(String[] args) {
        int count = 3;
        // fixed: decrement inside the loop, otherwise count > 0 forever
        while (count > 0) {
            System.out.println("countdown " + count);
            count--;
        }
    }
}
