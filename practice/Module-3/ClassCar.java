// slide 11
// class definitions
class Car {
    int speed;
    String color;
    String model;

    // methods (behavior)
    void start() {
        System.out.println("Car is starting...");
    }

    void stop() {
        System.out.println("Car is stopping...");
    }
}

// slide 12
//creating objects
public class ClassCar {
    public static void main(String[] args) {

        //creating objects of class Car
        Car myCar = new Car();
        Car yourCar = new Car();
        Car officeCar = new Car();

        // setting state
        myCar.speed = 120;
        myCar.color = "Red";
        myCar.model = "Toyota Camry";

        // Calling methods
        myCar.start();
        myCar.stop();
    }
}