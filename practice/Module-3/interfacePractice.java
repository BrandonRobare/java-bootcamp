interface Drawable {
    String UNIT = "cm";

    void draw();

    default void info() {
        System.out.println("  a shape, measured in " + UNIT);
    }

    static void help() {
        System.out.println("Drawable: implement draw(), get info() free");
    }
}

interface Resizable {
    void resize(double factor);
}

class Circle implements Drawable, Resizable {
    private double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing circle, radius " + radius + UNIT);
    }

    @Override
    public void resize(double factor) {
        radius *= factor;
    }
}

class Square implements Drawable {
    private double side;

    Square(double side) {
        this.side = side;
    }

    @Override
    public void draw() {
        System.out.println("Drawing square, side " + side + UNIT);
    }

    @Override
    public void info() {
        System.out.println("  a square, four equal sides in " + UNIT);
    }
}

public class interfacePractice {
    public static void main(String[] args) {
        Drawable.help();

        Drawable[] shapes = { new Circle(5.0), new Square(3.0) };
        for (Drawable shape : shapes) {
            shape.draw();
            shape.info();
        }

        Drawable first = shapes[0];
        // first.resize(2.0);
        ((Resizable) first).resize(2.0);
        first.draw();
    }
}
