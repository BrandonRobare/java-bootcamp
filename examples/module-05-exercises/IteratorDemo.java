import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {
        // DONE: wrap List.of(...) in new ArrayList<>(...) so removal is allowed
        List<String> titles = new ArrayList<>(List.of(
                "Java 21", "Deprecated Guide", "Clean Code", "Deprecated Notes"));

        // DONE: obtain an Iterator<String> from titles
        Iterator<String> iterator = titles.iterator();

        // DONE: loop while iterator.hasNext()
        while (iterator.hasNext()) {
            String title = iterator.next();

            if (title.startsWith("Deprecated")) {
                // DONE: remove through the iterator (not titles.remove)
                iterator.remove();
            }
        }

        System.out.println("Remaining: " + titles);
    }
}
