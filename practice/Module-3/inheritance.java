class Vehicle {
    String brand;
    int maxSpeed;

    void start() {
        System.out.println("Vehicle started");
    }

    void stop() {
        System.out.println("Vehicle stopped");
    }
}

//subclass
class Car extends Vehicle {
    int numDoors;
    void playMusic() {
        System.out.println("Playing music");
    }
}

public class inheritance {
    public static void main(String[] args) {
        Car myCar = new Car();
        myCar.start(); myCar.stop(); // inherited
        myCar.numDoors = 4; // owned
        myCar.playMusic();
        myCar.brand = "Toyota"; //inherited
        myCar .maxSpeed = 180;
        System.out.println("Brand: " + myCar.brand);
        System.out.println("Max Speed: " + myCar.maxSpeed);
    }
}