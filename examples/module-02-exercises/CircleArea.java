import java.util.Scanner;

public class CircleArea {
    public static void main(String[] args) {
        // DONE: Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        // DONE: read radius (parseDouble of nextLine)
        System.out.print("Radius: ");
        double r = Double.parseDouble(scanner.nextLine());

        // DONE: area = Math.PI * r * r  (Math.PI is java.lang — no import)
        double area = Math.PI * r * r;

        // DONE: printf with %.2f — formatting only, area keeps full precision
        System.out.printf("Area: %.2f%n", area);

        scanner.close();
    }
}
