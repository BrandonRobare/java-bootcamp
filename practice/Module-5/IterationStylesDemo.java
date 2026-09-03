import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class IterationStylesDemo {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("A", "B", "C"));

        // 1. for loop — the only style that gives you the index
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }

        // 2. enhanced for — clean, concise, read-only
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();

        // 5. forEach() + lambda (Java 8+)
        names.forEach(name -> System.out.print(name + " "));
        System.out.println();

        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
        map.forEach((k, v) -> System.out.println(k + " = " + v));

        // 3. Iterator — safe removal
        Iterator<String> it = names.iterator();
        while (it.hasNext()) {
            String name = it.next();
            if (name.equals("B")) {
                it.remove();   // safe removal
            }
        }
        System.out.println(names);   // [A, C]

        // 4. ListIterator — bidirectional
        ListIterator<String> li = names.listIterator();
        while (li.hasNext()) {          // forward
            System.out.print(li.next() + " ");
        }
        while (li.hasPrevious()) {      // backward
            System.out.print(li.previous() + " ");
        }
        System.out.println();

        // Removing any other way while iterating throws
        List<String> letters = new ArrayList<>(List.of("A", "B", "C", "D"));
        try {
            for (String letter : letters) {
                if (letter.equals("B")) {
                    letters.remove(letter);   // not safe
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException");
        }
    }
}
