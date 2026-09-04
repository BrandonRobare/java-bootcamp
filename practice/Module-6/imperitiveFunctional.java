import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class imperitiveFunctional {

    // HOW — you own the accumulator and the loop
    static List<Integer> imperative(List<Integer> numbers) {
        List<Integer> result = new ArrayList<>();
        for (int n : numbers) {
            if (n % 2 == 0) {
                result.add(n * 2);
            }
        }
        return result;
    }

    // WHAT — you name the steps, the library runs the loop
    static List<Integer> functional(List<Integer> numbers) {
        return numbers.stream()
                .filter(n -> n % 2 == 0)      // keep evens
                .map(n -> n * 2)              // double them
                .collect(Collectors.toList()); // terminal — back to a List
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        System.out.println("imperative: " + imperative(numbers)); // [4, 8, 12]
        System.out.println("functional: " + functional(numbers)); // [4, 8, 12]

        assert imperative(numbers).equals(functional(numbers)) : "same task, same answer";
    }
}
