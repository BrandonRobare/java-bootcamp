import java.util.Scanner;

public class LoopsDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // DONE: for loop
        System.out.println("Multiplication table for 5:");
        for (int i = 1; i <= 5; i++) {
            System.out.println("5 x " + i + " = " + (5 * i));
        }

        // DONE: while loop
        int count = 5;
        while (count >= 0) {
            System.out.println("countdown: " + count);
            --count;
        }

        // DONE: do-while
        String choice;
        do {
            System.out.print("Type 'menu' to see it again, anything else to quit: ");
            choice = scanner.nextLine();
            if (choice.equals("menu")) {
                System.out.println("1) Add  2) Withdraw  3) Exit");
            }
        } while (choice.equals("menu"));

        scanner.close();
    }
}
