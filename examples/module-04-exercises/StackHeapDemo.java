public class StackHeapDemo {
    static class Person {
        String name;
        Person(String name) {
            // DONE: this.name = name;
            this.name = name;
        }
    }

    static void printPerson(Person person) {
        // DONE: compute nameLength; print name + length
        int nameLength = person.name.length();
        System.out.println(person.name + " length: " + nameLength);

    }

    public static void main(String[] args) {
        // DONE: create Person on heap; call printPerson; print a local count
        Person person1 = new Person("Alice");
        printPerson(person1);

        int count = 1;
        System.out.println("Count: " + count);
    }
}
