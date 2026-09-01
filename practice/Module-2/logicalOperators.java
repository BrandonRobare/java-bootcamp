public class logicalOperators {

    // helper that prints when it runs
    static boolean check(String label, boolean value) {
        System.out.println("    (evaluated " + label + ")");
        return value;
    }

    public static void main(String[] args) {
        int age = 25;
        boolean hasLicense = true;

        // && AND — true only if BOTH sides are true
        System.out.println("age >= 18 && hasLicense : " + (age >= 18 && hasLicense));   // true

        // || OR — true if EITHER side is true
        System.out.println("age < 18 || hasLicense  : " + (age < 18 || hasLicense));    // true

        // ! NOT — flips the value
        System.out.println("!hasLicense             : " + (!hasLicense));               // false

        // Combining relational + logical
        int score = 85;
        if (score >= 80 && score <= 89) {
            System.out.println("Grade: B");
        }

        // Short-circuiting: && stops at the first false, || stops at the first true
        System.out.println("false && ... :");
        boolean r1 = check("left", false) && check("right", true);   // right never runs
        System.out.println("  result " + r1);

        System.out.println("true || ... :");
        boolean r2 = check("left", true) || check("right", true);    // right never runs
        System.out.println("  result " + r2);

        // ^ XOR — true only when the two sides DIFFER (from the slide)
        System.out.println("true  ^ true  : " + (true ^ true));    // false
        System.out.println("true  ^ false : " + (true ^ false));   // true

        // XOR does NOT short-circuit — both sides always run
        System.out.println("false ^ ... :");
        boolean r3 = check("left", false) ^ check("right", true);   // BOTH run
        System.out.println("  result " + r3);

        // Same trap with single & and | — they never short-circuit either
        System.out.println("false & ... :");
        boolean r4 = check("left", false) & check("right", true);   // right STILL runs
        System.out.println("  result " + r4);

        // the null check on the left guards the call on the right
        String name = null;
        if (name != null && name.length() > 0) {
            System.out.println("name has text");
        } else {
            System.out.println("short-circuit avoided a NullPointerException");
        }
    }
}
