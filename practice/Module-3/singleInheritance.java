// Dog reuses Animal's name, age, eat(), and sleep()
// adds breed and bark()
// private members never inherited

// superclass
class Animal {
    String name = "Animal";
    int age;

    void eat() {
        System.out.println(name + " is eating");
    }

    void sleep() {
        System.out.println(name + " is sleeping");
    }
}

//subclass
class Dog extends Animal {
    // NO 'String name' here — redeclaring it would HIDE Animal.name, not override it,
    // and Animal's eat()/sleep() would keep printing Animal's copy.
    String breed;

    // constructor
    Dog() {
        super(); // calls animal constructor
        System.out.println("dog constructor");
    }

    void bark() {
        System.out.println(name + " the " + breed + " is barking");
    }
}

public class singleInheritance {
    public static void main(String[] args) {
        Dog d = new Dog();

        // inherited fields
        d.name = "Tommy";
        d.age = 3;

        // own field
        d.breed = "Beagle";

        // inherited methods
        d.eat();
        d.sleep();

        // own method
        d.bark();
    }
}