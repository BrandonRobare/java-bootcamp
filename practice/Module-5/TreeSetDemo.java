import java.util.Comparator;
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {
        // Natural Ordering
        TreeSet<String> set = new TreeSet<>();
        set.add("Banana");
        set.add("Apple");
        set.add("Cherry");
        System.out.println(set);   // [Apple, Banana, Cherry]

        // Custom Ordering
        TreeSet<String> reverse = new TreeSet<>(Comparator.reverseOrder());
        reverse.addAll(set);
        System.out.println(reverse);   // [Cherry, Banana, Apple]

        TreeSet<Integer> nums = new TreeSet<>();
        for (int n : new int[] {50, 30, 70, 20, 40, 60, 80}) {
            nums.add(n);
        }
        System.out.println(nums);                  // in-order traversal: sorted
        System.out.println(nums.first());          // 20
        System.out.println(nums.last());           // 80
        System.out.println(nums.ceiling(45));      // least element >= 45
        System.out.println(nums.floor(45));        // greatest element <= 45
        System.out.println(nums.higher(40));       // least element > 40
        System.out.println(nums.lower(40));        // greatest element < 40
        System.out.println(nums.subSet(20, 60));   // range view
        System.out.println(nums.headSet(60));      // below 60
        System.out.println(nums.tailSet(60));      // at or above 60
        System.out.println(nums.descendingSet());  // reverse-order view

        // No null element allowed
        try {
            nums.add(null);
        } catch (NullPointerException e) {
            System.out.println("TreeSet rejects null");
        }
    }
}
