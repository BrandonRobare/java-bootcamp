// Module 3, slides 16-18 -- constructors.
// Own folder: Module-3/classCar.java already declares a class named Car.

// slide 16 -- default + parameterized
class Car {
    String brand;
    int speed;

    Car() {
        brand = "Unknown";
        speed = 0;
    }

    Car(String brand, int speed) {
        this.brand = brand;      // this.brand = field, brand = parameter
        this.speed = speed;
    }

    void display() {
        System.out.println(brand + " @ " + speed + " km/h");
    }
}

// slide 17 -- constructor overloading
class Student {
    String name;
    int age;

    Student() {
        name = "Unknown";
    }

    Student(String name) {
        this.name = name;
    }

    Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void display() {
        System.out.println(name + ", " + age);   // unset fields stay 0
    }
}

// slide 18 -- this() calls another constructor, must be the first statement
class Bank {
    String name;
    double balance;

    Bank(String name) {
        this(name, 0.0);
    }

    Bank(String name, double balance) {          // only this one assigns
        this.name = name;
        this.balance = balance;
    }

    void display() {
        System.out.printf("%s: %.2f%n", name, balance);
    }
}

public class constructorsPractice {
    public static void main(String[] args) {
        new Car().display();
        new Car("Toyota", 120).display();

        new Student().display();
        new Student("Alice").display();
        new Student("Bob", 21).display();

        new Bank("PNC").display();
        new Bank("PNC", 2500.75).display();
    }
}
