public class FunctionalInterfaceDemo {

    @FunctionalInterface
    interface MyFunctionalInterface {
        void perform(String message);   // the single abstract method
    }

    public static void main(String[] args) {
        // the lambda supplies the implementation of perform()
        MyFunctionalInterface obj = (message) -> System.out.println("Hello " + message);
        obj.perform("Java");            // Hello Java

        // same thing before lambdas existed
        MyFunctionalInterface old = new MyFunctionalInterface() {
            @Override
            public void perform(String message) {
                System.out.println("Hello " + message);
            }
        };
        old.perform("anonymous class");  // Hello anonymous class
    }
}
