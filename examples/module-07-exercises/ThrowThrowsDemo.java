public class ThrowThrowsDemo {
    // DONE: declare throws if using a checked exception in your design
    static void requirePositive(double amount) {
        if (amount <= 0) {
            // DONE: throw IllegalArgumentException (or your chosen type)
            throw new IllegalArgumentException ("Amount has to be positive");
        }
        System.out.println("Amount ok: " + amount);
    }

    public static void main(String[] args) {
        try {
            requirePositive(-5);
        } catch (IllegalArgumentException ex) {
            System.out.println("Rejected: " + ex.getMessage());
        }
        requirePositive(25);
    }
}
