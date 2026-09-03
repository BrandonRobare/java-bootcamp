public class IncrementDecrementOperators {
    public static void main(String[] args) {
        // Prefix: increment FIRST, then hand back the new value
        int a = 5;
        int b = ++a;
        System.out.println("int a = 5; int b = ++a;  -> a=" + a + " b=" + b);   // a=6 b=6

        // Postfix: hand back the CURRENT value, then increment
        int c = 5;
        int d = c++;
        System.out.println("int c = 5; int d = c++;  -> c=" + c + " d=" + d);   // c=6 d=5

        // Same split for decrement
        int e = 5;
        int f = --e;
        System.out.println("int e = 5; int f = --e;  -> e=" + e + " f=" + f);   // e=4 f=4

        int g = 5;
        int h = g--;
        System.out.println("int g = 5; int h = g--;  -> g=" + g + " h=" + h);   // g=4 h=5

        // On its own line the two are identical — the difference only shows
        // when you USE the result of the expression
        int i = 5;
        i++;    // same as ++i here
        System.out.println("i++ on its own line      -> i=" + i);              // 6

        // Where the off-by-one bugs come from: reading an array while incrementing
        int[] nums = {10, 20, 30};
        int idx = 0;
        System.out.println("nums[idx++] -> " + nums[idx++] + ", idx is now " + idx);  // 10, idx=1
        System.out.println("nums[++idx] -> " + nums[++idx] + ", idx is now " + idx);  // 30, idx=2
    }
}
