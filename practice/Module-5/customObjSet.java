import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


class Student {
    int id;
    String name;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student)) return false;
        return id == ((Student) o).id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


public class customObjSet {
    public static void main(String[] args) {
        Set<String> a = Set.of("A","B","C");
        Set<String> b = Set.of("B","C","D");

        // Union
        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        System.out.println(union); // [A,B,C,D]

        // Intersection
        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        System.out.println(intersection); // [B,C]

        // Difference
        Set<String> difference = new HashSet<>(a);
        difference.removeAll(b);
        System.out.println(difference); // [A]
    }
}
