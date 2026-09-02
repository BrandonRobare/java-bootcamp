import java.util.Scanner;

public class PersonalDetails {
    public static void main(String[] args) {
        // DONE: Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(System.in);

        // DONE: read name (nextLine), age (nextInt), consume leftover newline, city (nextLine)
        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("City: ");
        String city = scanner.nextLine();

        // DONE: printf greeting
        System.out.printf("Hello, %s! You are %d and live in %s.%n", name, age, city);

        scanner.close();
    }
}
