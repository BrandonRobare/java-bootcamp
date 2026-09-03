class Person {
    private String name;
    private int age;

    public Person (String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void introduce () {
        System.out.println("Hi, I am " + name + " and I am " + age + " years old .");
    }

    public int getAge() { return age; }
}

public class RealWorldClass {
    public static void main(String[] args) {
        Person p1 = new Person("Alice", 21);
        p1.introduce();
        System.out.println("Age: " + p1.getAge());
    }
}