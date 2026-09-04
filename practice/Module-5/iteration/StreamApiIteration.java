import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamApiIteration {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(List.of("Ann", "Alec", "Ben", "Cal"));

        names.stream()
             .filter(s -> s.startsWith("A"))
             .map(String::toUpperCase)
             .forEach(System.out::println);   // ANN ALEC

        // a stream does not modify the source -- collect into a new list
        List<String> upper = names.stream()
                                  .map(String::toUpperCase)
                                  .collect(Collectors.toList());
        System.out.println(upper);
        System.out.println(names);   // unchanged

        int[] arr = {10, 20, 30, 40, 50};
        System.out.println(Arrays.stream(arr).sum());       // 150
        System.out.println(Arrays.stream(arr).max().getAsInt());

        System.out.println(names.stream().filter(s -> s.length() == 3).count());   // 3
        System.out.println(names.stream().collect(Collectors.joining(", ")));

        Map<Character, List<String>> byInitial =
                names.stream().collect(Collectors.groupingBy(s -> s.charAt(0)));
        System.out.println(byInitial);

        // forEach() on the collection itself -- same lambda, no pipeline
        names.forEach(name -> System.out.print(name + " "));
        System.out.println();
    }
}
