class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("Dog barks");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat meows");
    }
}

public class dynamicMethodDispatch {
    public static void main(String[] args) {
        Animal a1 = new Dog();
        a1.sound();

        Animal a2 = new Cat();
        a2.sound();

        Animal a3 = new Animal();
        a3.sound();
    }
}
