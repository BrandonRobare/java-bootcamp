import java.util.Arrays;
import java.util.List;

public class StreamPipelineDemo {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        numbers.stream()                   // source
                .filter(n -> n % 2 == 0)   // intermediate — [2, 4, 6]
                .map(n -> n * n)           // intermediate — [4, 16, 36]
                .sorted()                  // intermediate — [4, 16, 36]
                .forEach(System.out::println); // terminal — 4, 16, 36

        // nothing above runs until forEach() is called
        numbers.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n);          // no terminal op, no output

        System.out.println(numbers);       // [1, 2, 3, 4, 5, 6] — source untouched
    }
}
