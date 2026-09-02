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
// slides 20-21 -- all four access modifiers on one class
class Bank {
    private String accountNumber;   // this class only
    double balance;                 // default: same package
    protected double interestRate;  // package + subclasses
    public String bankName;         // everyone

    Bank(String accountNumber) {
        this(accountNumber, 0.0);
    }

    Bank(String accountNumber, double balance) {   // only this one assigns
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.interestRate = 4.5;
        this.bankName = "Trusted Bank";
    }

    public String getAccountNumber() {             // the only way in from outside
        return accountNumber;
    }

    void display() {
        System.out.printf("%s %s: %.2f @ %.1f%%%n", bankName, accountNumber, balance, interestRate);
    }
}

public class constructorsPractice {
    public static void main(String[] args) {
        new Car().display();
        new Car("Toyota", 120).display();

        new Student().display();
        new Student("Alice").display();
        new Student("Bob", 21).display();

        Bank b = new Bank("PNC-001");
        b.display();
        new Bank("PNC-002", 2500.75).display();

        System.out.println(b.bankName);            // public: fine
        System.out.println(b.balance);             // default: fine, same package
        // System.out.println(b.accountNumber);    // private: won't compile
        System.out.println(b.getAccountNumber());  // go through the getter instead
    }
}
