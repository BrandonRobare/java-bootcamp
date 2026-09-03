import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Apple");            // [Apple]
        list.add("Banana");           // [Apple, Banana]
        list.add(1, "Blueberry");     // [Apple, Blueberry, Banana] — shifts, O(n)

        System.out.println(list.get(2));        // Banana
        list.set(2, "Blackberry");              // updates index 2
        list.remove("Apple");                   // [Blueberry, Blackberry]
        System.out.println(list);

        for (String fruit : list) {
            System.out.print(fruit + " ");
        }
        System.out.println();

        System.out.println(list.indexOf("Blackberry"));  // 1
        System.out.println(list.size());                 // 2
        System.out.println(list.isEmpty());              // false

        ArrayList<String> fruit = new ArrayList<>();
        fruit.add("Apple");
        fruit.add("Banana");
        fruit.add("Cherry");
        fruit.add(1, "Blueberry");   // insert at index 1

        System.out.println(fruit.get(2));   // Banana
        System.out.println(fruit);          // [Apple, Blueberry, Banana, Cherry]
        fruit.remove("Banana");
        System.out.println(fruit);          // [Apple, Blueberry, Cherry]
    }
}
