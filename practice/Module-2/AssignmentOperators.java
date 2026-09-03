public class AssignmentOperators {
    public static void main(String[] args) {
        // = simple assignment
        int a = 10;
        System.out.println("a = 10        -> " + a);

        // Compound assignment: a += 5 is shorthand for a = a + 5
        a += 5;   System.out.println("a += 5        -> " + a);   // 15
        a *= 2;   System.out.println("a *= 2        -> " + a);   // 30
        a -= 8;   System.out.println("a -= 8        -> " + a);   // 22
        a /= 2;   System.out.println("a /= 2        -> " + a);   // 11
        a %= 4;   System.out.println("a %= 4        -> " + a);   // 3  (remainder)

        // Bitwise compound forms — same shorthand idea, on the bits
        int b = 12;                                    // 1100
        b &= 10;  System.out.println("12 &= 10      -> " + b);   // 8   (1000)
        b |= 3;   System.out.println("   |= 3       -> " + b);   // 11  (1011)
        b ^= 1;   System.out.println("   ^= 1       -> " + b);   // 10  (1010)
        b <<= 2;  System.out.println("   <<= 2      -> " + b);   // 40  (shift left = x4)
        b >>= 1;  System.out.println("   >>= 1      -> " + b);   // 20  (shift right = /2)

        // Bonus: compound assignment casts for you, plain assignment does not
        byte small = 10;
        small += 5;              // works — implicit (byte) cast is built in
        // small = small + 5;    // would NOT compile: int cannot fit in byte
        System.out.println("byte small += 5 -> " + small);
    }
}
