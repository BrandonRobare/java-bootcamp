public class ObjectLifecycleDemo {
    static class Person {
        final String name;
        Person(String name) { this.name = name; }
    }

    public static void main(String[] args) {
        // DONE: create aliases a/b; null them; note when object is GC-eligible
        Person first = new Person("Aman");
        Person alias = first;

        System.out.println("Same object: " + (first == alias));

        first = null;
        System.out.println("Still reachable through alias: " + alias.name);

        alias = null;
        System.out.println("No strong references remain; object is GC-eligible.");

        System.gc();
        System.out.println("GC requested, not guaranteed.");
    }
}
