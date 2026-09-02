import java.util.Scanner;

public class BillSummary {
    public static void main(String[] args) {
        // DONE: Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        // DONE: read product (nextLine), quantity (parseInt), unit price (parseDouble)
        System.out.print("Product: ");
        String product = scanner.nextLine();

        System.out.print("Quantity: ");
        int qty = Integer.parseInt(scanner.nextLine());

        System.out.print("Unit price: ");
        double price = Double.parseDouble(scanner.nextLine());

        // DONE: total, 10% discount, amount due
        double total = qty * price;
        double discount = total * 0.10;
        double due = total - discount;

        // DONE: printf summary — %% escapes the percent sign
        System.out.println("--- Bill Summary ---");
        System.out.printf("Item:            %s%n", product);
        System.out.printf("Quantity:        %d%n", qty);
        System.out.printf("Unit price:      %.2f%n", price);
        System.out.printf("Subtotal:        %.2f%n", total);
        System.out.printf("Discount (10%%):  %.2f%n", discount);
        System.out.printf("Amount due:      %.2f%n", due);

        scanner.close();
    }
}
