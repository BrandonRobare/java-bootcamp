import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EnhancedForIteration {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();

        List<String> names = new ArrayList<>(List.of("Ann", "Ben", "Cal"));
        for (String name : names) {
            System.out.print(name + " ");
        }
        System.out.println();

        Map<String, Integer> ages = new LinkedHashMap<>();
        ages.put("Ann", 34);
        ages.put("Ben", 28);
        for (Map.Entry<String, Integer> e : ages.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }
        for (String key : ages.keySet()) {
            System.out.print(key + " ");
        }
        System.out.println();

        // no index, and assigning to the loop variable changes nothing
        for (String name : names) {
            name = name.toUpperCase();
        }
        System.out.println(names);   // [Ann, Ben, Cal]

        // structural change during the loop throws on the next hasNext()/next()
        List<String> letters = new ArrayList<>(List.of("A", "B", "C", "D"));
        try {
            for (String letter : letters) {
                if (letter.equals("B")) {
                    letters.remove(letter);
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("ConcurrentModificationException");
        }
    }
}
