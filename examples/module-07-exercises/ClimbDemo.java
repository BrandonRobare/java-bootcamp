/*
 * ClimbDemo - the four things that tripped me up in Module 7.
 * Run it, then break it on purpose (each PART says how).
 */
public class ClimbDemo {

    // ============================================================
    // PART 1 - WHICH WAY DOES IT GO?
    //
    //   main ──calls──► caseB ──calls──► risky
    //                                      │ throw   <-- BORN here
    //   main ◄──climbs── caseB ◄──climbs───┘
    //
    // Calls go DOWN. Exceptions climb UP.
    // risky() is the START of the climb, never a destination.
    // ============================================================

    static void risky() {
        throw new ArithmeticException("/ by zero");
        //                            ^ this String is the MESSAGE.
        //                              read it back later with e.getMessage()
    }

    // A - catch type MATCHES -> climb stops here, method finishes normally
    static void caseA() {
        try {
            risky();
        } catch (ArithmeticException e) {
            System.out.println("caseA: caught locally");
        }
        System.out.println("caseA: continues normally");
    }

    // B - catch type does NOT match -> same as having no catch at all
    static void caseB() {
        try {
            risky();
        } catch (NumberFormatException e) {
            // NumberFormatException is a SIBLING of ArithmeticException, not a parent.
            // A catch matches the thrown type or an ANCESTOR of it. Siblings never match.
            System.out.println("caseB: never prints");
        }
        System.out.println("caseB: never prints either");
        // ^^ A METHOD THAT DOESN'T CATCH DOESN'T GET TO FINISH.
        //    caseB is abandoned the moment no catch matched.
    }

    // ============================================================
    // PART 2 - A CATCH ONLY GUARDS ITS OWN try { } BOX
    // ============================================================

    static void outsideTheBox() {
        try {
            System.out.println("outsideTheBox: the try box covers ONLY these lines");
        } catch (ArithmeticException e) {
            System.out.println("outsideTheBox: never prints");
        }

        risky();
        // ^^ OUTSIDE the box. The catch three lines up is the RIGHT TYPE
        //    and still cannot see this call. Not in the box = not covered.
        //    This one climbs to main.
    }

    // ============================================================
    // PART 3 - SAME CLIMB, BUT CHECKED (this is all `throws` does)
    //
    // Reuses InsufficientFundsException (extends Exception = CHECKED).
    // Compare the output to PART 1: IDENTICAL.
    // `throws` fires nothing, stops nothing, prints nothing.
    // It only forces you to WRITE the clause. Handle or declare.
    // ============================================================

    static void checkedRisky() throws InsufficientFundsException {
        // CLAUSE REQUIRED: throws it, doesn't catch it -> it escapes
        throw new InsufficientFundsException(100, 500);
    }

    static void checkedMiddle() throws InsufficientFundsException {
        // CLAUSE REQUIRED: has a try AND a catch, but the WRONG TYPE,
        // so it still escapes. A try alone does not satisfy the compiler.
        try {
            checkedRisky();
        } catch (NumberFormatException e) {
            System.out.println("checkedMiddle: never prints");
        }
    }
    // NOTE: caseA/caseB above need NO clause - ArithmeticException is UNCHECKED.
    // Unchecked forces nothing on anybody. Only checked gets chased by the compiler.
    //
    // BREAK IT: delete the clause on checkedMiddle ->
    //   "unreported exception InsufficientFundsException; must be caught or declared"

    // ============================================================
    // PART 4 - finally RUNS ON EVERY EXIT
    // ============================================================

    static void finallyDemo(int n) {
        try {
            System.out.println("  start");
            if (n == 1) throw new IllegalStateException("boom");
            if (n == 2) return;
            System.out.println("  end of try");
        } catch (IllegalStateException e) {
            System.out.println("  caught: " + e.getMessage());
        } finally {
            System.out.println("  finally");
            // runs when: nothing thrown / thrown+caught / return / thrown+NOT caught.
            // Only System.exit() or a JVM crash skips it.
        }
        System.out.println("  after");
    }

    public static void main(String[] args) {
        System.out.println("--- PART 1 ---");
        caseA();
        try {
            caseB();
        } catch (ArithmeticException e) {
            System.out.println("main: caught the climber from caseB");
        }

        System.out.println("--- PART 2 ---");
        try {
            outsideTheBox();
        } catch (ArithmeticException e) {
            System.out.println("main: caught it - the catch next door never applied");
        }

        System.out.println("--- PART 3 ---");
        try {
            checkedMiddle();
        } catch (InsufficientFundsException e) {
            System.out.println("main: caught the checked climber -> " + e.getMessage());
        }

        System.out.println("--- PART 4 ---");
        System.out.println(" finallyDemo(0) nothing thrown:");
        finallyDemo(0);
        System.out.println(" finallyDemo(1) thrown + caught:");
        finallyDemo(1);
        System.out.println(" finallyDemo(2) return inside try:");
        finallyDemo(2);
    }
}
