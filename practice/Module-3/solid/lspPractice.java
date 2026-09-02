public class lspPractice {

    // Violates LSP - Square breaks Rectangle's contract
    static class Bad {
        static class Rectangle {
            protected int width;
            protected int height;

            void setWidth(int w) { width = w; }
            void setHeight(int h) { height = h; }

            int area() { return width * height; }
        }

        static class Square extends Rectangle {
            @Override
            void setWidth(int w) { width = height = w; }

            @Override
            void setHeight(int h) { width = height = h; }
        }
    }

    // Follows LSP - use abstraction
    static class Good {
        interface Shape {
            double area();
        }

        static class Rectangle implements Shape {
            private final int width;
            private final int height;

            Rectangle(int width, int height) { this.width = width; this.height = height; }

            public double area() { return width * height; }
        }

        static class Square implements Shape {
            private final int side;

            Square(int side) { this.side = side; }

            public double area() { return side * side; }
        }
    }

    public static void main(String[] args) {
        System.out.println("bad:  " + resize(new Bad.Rectangle()));
        System.out.println("bad:  " + resize(new Bad.Square()));

        Good.Shape[] shapes = { new Good.Rectangle(5, 4), new Good.Square(5) };
        for (Good.Shape shape : shapes) {
            System.out.printf("good %-10s area %.0f%n",
                    shape.getClass().getSimpleName() + ":", shape.area());
        }
    }

    static String resize(Bad.Rectangle r) {
        r.setWidth(5);
        r.setHeight(4);
        return r.getClass().getSimpleName() + " 5x4 - expected area 20, got " + r.area();
    }
}
