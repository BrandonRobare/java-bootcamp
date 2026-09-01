public class relationalOperators {
    public static void main(String[] args) {
        int a = 7, b = 2;

        System.out.println("a == b : " + (a == b));   // false — equal to
        System.out.println("a != b : " + (a != b));   // true  — not equal to
        System.out.println("a >  b : " + (a > b));    // true
        System.out.println("a <  b : " + (a < b));    // false
        System.out.println("a >= b : " + (a >= b));   // true
        System.out.println("a <= b : " + (a <= b));   // false

        // Every relational operator produces a boolean, so you can store it
        boolean isOlder = a > b;
        System.out.println("stored result: " + isOlder);

        // ...and use it directly as an if condition
        if (a % 2 == 0) {
            System.out.println(a + " is even");
        } else {
            System.out.println(a + " is odd");
        }

        // Trap: == on objects compares addresses, not contents
        String x = new String("hi");
        String y = new String("hi");
        System.out.println("x == y      : " + (x == y));        // false — different objects
        System.out.println("x.equals(y) : " + x.equals(y));     // true  — same characters
    }
}
