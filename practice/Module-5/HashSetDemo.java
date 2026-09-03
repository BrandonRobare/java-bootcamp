import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Apple");
        set.add("Banana");
        set.add("Cherry");
        set.add("Apple"); //ignored duplicate

        set.add(null); // only one null kept
        set.add(null);

        System.out.println(set.contains("Banana"));
        System.out.println(set.remove("Apple"));
        System.out.println(set); // [Banana, null, Cherry]

    }
}