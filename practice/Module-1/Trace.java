public class Trace {
    public static void main(String[] args) {
        System.out.println("Trace Testing");
    }
}

/*
javac Trace.java
javap -c Trace
java -Xlog:class+load=info Trace | head -20
java -XX:+PrintCompilation Trace 2>&1 | head -20
 */
