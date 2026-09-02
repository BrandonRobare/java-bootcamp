import java.util.Scanner;

public class ProductInfo {
    public static void main(String[] args) {
        // DONE: Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        // DONE: read name (nextLine), quantity (parseInt of nextLine), price (parseDouble of nextLine)
        System.out.print("Product name: ");
        String name = scanner.nextLine();              // may contain spaces

        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());

        System.out.print("Unit price: ");
        double price = Double.parseDouble(scanner.nextLine());

        // DONE: compute total = qty * price
        double total = qty * price;

        // DONE: printf summary
        System.out.printf("%s x%d @ %.2f%n", name, qty, price);
        System.out.printf("Total: %.2f%n", total);

        scanner.close();
    }
}
