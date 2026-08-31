public class Methods {
    public static void main(String[] args) {
        // FIX: int sum = add(10, 20); then print sum
        int sum = add(10, 20);
        System.out.println(sum);
        // FIX: String message = greet("Aman"); then print message
        String message = greet("Aman");
        System.out.println(message);
    }

    public static int add(int a, int b) {
        // FIX: return a + b
        return a + b;
    }

    public static String greet(String name) {
        // FIX: return "Hello, " + name + "!"
        return "Hello, " + name + "!";
    }
}
