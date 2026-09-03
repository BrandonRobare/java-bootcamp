public class DipPractice {

    // Tight coupling to a concrete class
    static class Bad {
        static class MySQLDatabase {
            void save(String data) { System.out.println("bad:  MySQL saved " + data); }
        }

        static class UserService {
            private final MySQLDatabase db = new MySQLDatabase();

            void register(String user) { db.save(user); }
        }
    }

    // Depend on an abstraction
    static class Good {
        interface Database {
            void save(String data);
        }

        static class MySQLDatabase implements Database {
            public void save(String data) { System.out.println("good: MySQL saved " + data); }
        }

        static class PostgresDatabase implements Database {
            public void save(String data) { System.out.println("good: Postgres saved " + data); }
        }

        static class UserService {
            private final Database db;

            UserService(Database db) { this.db = db; }

            void register(String user) { db.save(user); }
        }
    }

    public static void main(String[] args) {
        new Bad.UserService().register("aman");

        new Good.UserService(new Good.MySQLDatabase()).register("aman");
        new Good.UserService(new Good.PostgresDatabase()).register("aman");
    }
}
