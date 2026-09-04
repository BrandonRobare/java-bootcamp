// For loop -- index-based.
// Good for: arrays and Lists when you need the index -- set(i, ..), reverse, stride.
// Bad at:   Sets and Maps (no index), LinkedList (O(n^2)), forward removal (skips).
import java.util.ArrayList;
import java.util.List;

public class ForLoopIteration {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30, 40, 50};
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + ": " + arr[i]);
        }

        List<String> names = new ArrayList<>(List.of("Ann", "Ben", "Cal"));
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }

        // index is the whole point -- set() and backwards need it
        for (int i = 0; i < names.size(); i++) {
            names.set(i, names.get(i).toUpperCase());
        }
        for (int i = names.size() - 1; i >= 0; i--) {
            System.out.print(names.get(i) + " ");   // CAL BEN ANN
        }
        System.out.println();

        // removing while looping forward skips an element
        List<String> letters = new ArrayList<>(List.of("A", "B", "B", "C"));
        for (int i = letters.size() - 1; i >= 0; i--) {   // backwards is safe
            if (letters.get(i).equals("B")) {
                letters.remove(i);
            }
        }
        System.out.println(letters);   // [A, C]
    }
}
