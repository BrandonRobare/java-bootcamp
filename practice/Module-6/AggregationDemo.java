import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AggregationDemo {

    record Person(String name, String city) {}

    public static void main(String[] args) {
        List<Integer> numbers = List.of(2, 4, 6, 8, 10);
        List<String> names = List.of("Alice", "Bob", "Charlie");
        List<Person> people = List.of(
                new Person("Alice", "Pittsburgh"),
                new Person("Bob", "Cleveland"),
                new Person("Charlie", "Pittsburgh"));

        // examples
        int sum = numbers.stream()
                .mapToInt(Integer::intValue)
                .sum();

        long count = numbers.stream()
                .count();

        System.out.println("sum: " + sum);
        System.out.println("count: " + count);

        double avg = numbers.stream()
                .mapToDouble(Integer::doubleValue)
                .average()
                .orElse(0.0);

        Optional<Integer> min = numbers.stream().min(Integer::compare);
        Optional<Integer> max = numbers.stream().max(Integer::compare);

        System.out.println("avg: " + avg);
        System.out.println("min: " + min);
        System.out.println("max: " + max);

        int total = numbers.stream()
                .reduce(0, (a, b) -> a + b);

        List<String> list = names.stream()
                .collect(Collectors.toList());

        Map<String, Long> byCity = people.stream()
                .collect(Collectors.groupingBy(Person::city, Collectors.counting()));

        System.out.println("reduce: " + total);   // 30
        System.out.println("collect: " + list);   // [Alice, Bob, Charlie]
        System.out.println("byCity: " + byCity);  // {Cleveland=1, Pittsburgh=2}
    }
}