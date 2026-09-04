// ListIterator -- bi-directional, Lists only.
// Good for: walking backwards, and set() / add() editing mid-traversal.
// Bad at:   no Set or Map, most verbose, add() is skipped by the current pass.
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListIteratorIteration {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Ann", "Ben", "Cal"));

        ListIterator<String> li = names.listIterator();
        while (li.hasNext()) {
            System.out.print(li.nextIndex() + "=" + li.next() + " ");   // 0=Ann 1=Ben 2=Cal
        }
        System.out.println();
        while (li.hasPrevious()) {
            System.out.print(li.previous() + " ");                      // Cal Ben Ann
        }
        System.out.println();

        // set() and add() are ListIterator-only
        ListIterator<String> editor = names.listIterator();
        while (editor.hasNext()) {
            String name = editor.next();
            if (name.equals("Ben")) {
                editor.set("BEN");
                editor.add("Bex");   // inserted after BEN, skipped by this pass
            }
        }
        System.out.println(names);   // [Ann, BEN, Bex, Cal]

        // start at the end and walk backwards
        ListIterator<String> tail = names.listIterator(names.size());
        while (tail.hasPrevious()) {
            System.out.print(tail.previous() + " ");
        }
        System.out.println();
    }
}
