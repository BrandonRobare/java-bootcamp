import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {
        TreeMap<Integer, String> map = new TreeMap<>();
        map.put(50, "Fifty");
        map.put(20, "Twenty");
        map.put(70, "Seventy");
        System.out.println(map);                  // {20=Twenty, 50=Fifty, 70=Seventy}
        System.out.println(map.ceilingKey(35));   // 50
        System.out.println(map.subMap(30, 70));   // {50=Fifty}

        System.out.println(map.firstKey());       // 20
        System.out.println(map.lastKey());        // 70
        System.out.println(map.floorKey(35));     // 20
        System.out.println(map.higherKey(50));    // 70
        System.out.println(map.lowerKey(50));     // 20
        System.out.println(map.headMap(50));      // keys below 50
        System.out.println(map.tailMap(50));      // keys at or above 50
        System.out.println(map.descendingMap());  // reverse-order view

        // No null keys allowed
        try {
            map.put(null, "Nope");
        } catch (NullPointerException e) {
            System.out.println("TreeMap rejects null keys");
        }
    }
}
