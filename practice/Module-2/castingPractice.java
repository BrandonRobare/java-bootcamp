public class castingPractice {
    public static void main(String[] args) {
        // Widening (implicit) - compiler does it, no data loss
        int i = 100;
        long l = i;        // int -> long
        float f = l;       // long -> float
        double d1 = f;     // float -> double
        System.out.println("Widening: " + i + " -> " + l + " -> " + f + " -> " + d1);

        // Narrowing (explicit) - programmer must cast, data can be lost
        double d = 123.456;
        int n = (int) d;           // 123, decimal dropped
        long big = 1000L;
        short s = (short) big;     // 1000
        System.out.println("Narrowing: " + d + " -> " + n + ", " + big + " -> " + s);

        // Data loss example: byte holds -128..127
        int x = 1000;
        byte b = (byte) x;         // -24
        System.out.println("Overflow: " + x + " -> " + b);
    }
}