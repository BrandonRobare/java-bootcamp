public class ocpPractice {

    // Violates OCP - must edit for every shape
    static class Bad {
        static class ShapeAreaCalculator {
            double area(String type, double r, double l, double w) {
                if (type.equals("CIRCLE")) {
                    return 3.14 * r * r;
                }
                if (type.equals("RECTANGLE")) {
                    return l * w;
                }
                return 0;
            }
        }
    }

    // Follows OCP - extend, don't modify
    static class Good {
        interface Shape {
            double area();
        }

        static class Circle implements Shape {
            private final double r;

            Circle(double r) { this.r = r; }

            public double area() { return 3.14 * r * r; }
        }

        static class Rectangle implements Shape {
            private final double l;
            private final double w;

            Rectangle(double l, double w) { this.l = l; this.w = w; }

            public double area() { return l * w; }
        }

        static class Triangle implements Shape {
            private final double b;
            private final double h;

            Triangle(double b, double h) { this.b = b; this.h = h; }

            public double area() { return 0.5 * b * h; }
        }
    }

    public static void main(String[] args) {
        Bad.ShapeAreaCalculator calc = new Bad.ShapeAreaCalculator();
        System.out.printf("bad  CIRCLE:   %.2f%n", calc.area("CIRCLE", 2, 0, 0));
        System.out.printf("bad  TRIANGLE: %.2f%n", calc.area("TRIANGLE", 0, 6, 5));

        Good.Shape[] shapes = {
            new Good.Circle(2),
            new Good.Rectangle(3, 4),
            new Good.Triangle(6, 5)
        };
        for (Good.Shape shape : shapes) {
            System.out.printf("good %-11s%.2f%n",
                    shape.getClass().getSimpleName() + ":", shape.area());
        }
    }
}
