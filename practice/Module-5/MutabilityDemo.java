import java.util.List;

public class MutabilityDemo {
    public static void main(String[] args) {
        // MUTABLE (StringBuilder) — same object is modified
        StringBuilder sb = new StringBuilder("Hello");
        System.out.println(sb);   // Hello
        sb.append(" World");
        System.out.println(sb);   // Hello World

        // IMMUTABLE (String) — new object is created, s1 unchanged
        String s1 = "Hello";
        String s2 = s1.concat(" World");
        System.out.println(s1);   // Hello
        System.out.println(s2);   // Hello World

        // Immutable collections
        List<String> fixed = List.of("A", "B", "C");
        try {
            fixed.add("D");
        } catch (UnsupportedOperationException e) {
            System.out.println("List.of() is immutable");
        }
    }
}
