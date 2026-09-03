import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Apple");
        map.put(2, "Banana");
        map.put(3, "Cherry");
        map.put(2, "Blueberry");   // updates value for key 2

        System.out.println(map.get(1));   // Apple
        System.out.println(map.get(2));   // Blueberry

        map.put(null, "No Key");   // one null key allowed
        map.put(4, null);          // multiple null values allowed

        System.out.println(map.containsKey(3));            // true
        System.out.println(map.containsValue("Blueberry")); // true
        map.remove(3);

        System.out.println(map.keySet());
        System.out.println(map.values());
        for (Map.Entry<Integer, String> e : map.entrySet()) {
            System.out.println(e.getKey() + " -> " + e.getValue());
        }

        Map<String, Integer> nulls = new HashMap<>();
        nulls.put(null, 100);
        nulls.put(null, 200);      // updates the null key
        System.out.println(nulls.get(null));   // 200
    }
}
