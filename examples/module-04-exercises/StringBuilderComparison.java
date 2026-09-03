public class StringBuilderComparison {
    private static final int ITERATIONS = 50_000;

    static String withString() {
        String result = "";
        for (int i = 0; i < ITERATIONS; i++) {
            // DONE: result += "x";  (each update creates another String)
            result += "x";
        }
        return result;
    }

    static String withBuilder() {
        // Initial capacity avoids repeated buffer growth.
        // DONE: StringBuilder result = new StringBuilder(ITERATIONS);
        StringBuilder result = new StringBuilder(ITERATIONS);
        for (int i = 0; i < ITERATIONS; i++) {
            // DONE: result.append('x');
            result.append('x');
        }
        // DONE: return result.toString();
        return result.toString();
    }

    public static void main(String[] args) {
        // DONE: time withString() with System.nanoTime()
        long stringStart = System.nanoTime();
        String s = withString();
        long stringNanos = System.nanoTime() - stringStart;

        // DONE: time withBuilder() with System.nanoTime()
        long builderStart = System.nanoTime();
        String b = withBuilder();
        long builderNanos = System.nanoTime() - builderStart;

        // DONE: printf both lengths and ms (stringNanos / 1_000_000.0)
        System.out.printf("String: %d chars, %.3f ms%n", s.length(), stringNanos / 1_000_000.0);
        System.out.printf("StringBuilder: %d chars, %.3f ms%n", b.length(), builderNanos / 1_000_000.0);
    }
}
