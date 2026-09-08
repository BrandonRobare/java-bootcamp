void methodC() { int x = 10/0;}
void methodB() { methodC(); }
void method A() { methodB(); }

public static void main(String[] args) {
    try { new App.methodA(); }
    catch (ArithmeticException e) { /* handled here */ }
}