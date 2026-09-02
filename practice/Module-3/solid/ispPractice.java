public class ispPractice {

    // Fat interface forces unused methods
    static class Bad {
        interface Worker {
            void work();
            void eat();
            void drive();
        }

        static class HumanWorker implements Worker {
            public void work() { System.out.println("bad:  working"); }

            public void eat() { System.out.println("bad:  eating"); }

            public void drive() {
                throw new UnsupportedOperationException("not all workers can drive");
            }
        }
    }

    // Small, focused interfaces
    static class Good {
        interface Workable { void work(); }

        interface Eatable { void eat(); }

        interface Drivable { void drive(); }

        static class HumanWorker implements Workable, Eatable {
            public void work() { System.out.println("good: human working"); }

            public void eat() { System.out.println("good: human eating"); }
        }

        static class RobotWorker implements Workable, Drivable {
            public void work() { System.out.println("good: robot working"); }

            public void drive() { System.out.println("good: robot driving"); }
        }
    }

    public static void main(String[] args) {
        Bad.Worker bad = new Bad.HumanWorker();
        bad.work();
        bad.eat();
        try {
            bad.drive();
        } catch (UnsupportedOperationException e) {
            System.out.println("bad:  " + e.getMessage());
        }

        Good.HumanWorker human = new Good.HumanWorker();
        human.work();
        human.eat();

        Good.RobotWorker robot = new Good.RobotWorker();
        robot.work();
        robot.drive();
    }
}
