import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IteratorIteration {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(List.of(10, 20, 30, 40, 50));
        Iterator<Integer> it = numbers.iterator();
        while (it.hasNext()) {
            Integer value = it.next();
            System.out.print(value + " ");
        }
        System.out.println();

        // the reason to reach for Iterator: removal mid-traversal
        Iterator<Integer> remover = numbers.iterator();
        while (remover.hasNext()) {
            if (remover.next() % 20 == 0) {
                remover.remove();
            }
        }
        System.out.println(numbers);   // [10, 30, 50]

        Set<String> tags = new HashSet<>(Set.of("java", "sql", "git"));
        Iterator<String> tagIt = tags.iterator();
        while (tagIt.hasNext()) {
            if (tagIt.next().startsWith("s")) {
                tagIt.remove();
            }
        }
        System.out.println(tags.size());   // 2

        Map<String, Integer> stock = new LinkedHashMap<>();
        stock.put("bolt", 0);
        stock.put("nut", 12);
        Iterator<Map.Entry<String, Integer>> entries = stock.entrySet().iterator();
        while (entries.hasNext()) {
            if (entries.next().getValue() == 0) {
                entries.remove();
            }
        }
        System.out.println(stock);   // {nut=12}

        // next() without hasNext() on an empty run throws NoSuchElementException
    }
}
