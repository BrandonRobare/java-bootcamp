import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("First number: ");
        // FIX: double a = Double.parseDouble(scanner.nextLine());
        double a = Double.parseDouble(scanner.nextLine());

        System.out.print("Second number: ");
        // FIX: double b = Double.parseDouble(scanner.nextLine());
        double b = Double.parseDouble(scanner.nextLine());

        // FIX: printf Sum, Difference, Product, Quotient with %.2f
        System.out.printf("Sum: %.2f%n", a + b);
        System.out.printf("Difference: %.2f%n", a - b);
        System.out.printf("Product: %.2f%n", a * b);
        System.out.printf("Quotient: %.2f%n", a / b);

        scanner.close();
    }
}
