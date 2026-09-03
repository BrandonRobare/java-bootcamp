// slide 3

class Car {
    String color;
    int speed;

    void drive() {
        System.out.println("Driving");
    }
}

public class DataTypes {
    public static void main(String[] args) {
        Car myCar = new Car(); //obj
        myCar.color = "Red";
        myCar.speed = 120;

        System.out.println(myCar.color + " car doing " + myCar.speed);
        myCar.drive();
    }
}
