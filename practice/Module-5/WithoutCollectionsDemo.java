import java.util.ArrayList;
import java.util.List;

public class WithoutCollectionsDemo {
    public static void main(String[] args) {
        // WITHOUT COLLECTIONS — manual resizing
        String[] users = new String[2];
        int size = 0;

        if (size == users.length) {
            String[] newArr = new String[users.length * 2];
            System.arraycopy(users, 0, newArr, 0, size);
            users = newArr;
        }
        users[size++] = "Alice";  // lots of manual work!
        System.out.println(users[0] + " (capacity " + users.length + ")");

        // WITH COLLECTIONS — no manual resizing
        List<String> list = new ArrayList<>();
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");
        for (String user : list) {
            System.out.println(user);
        }
        // Simple, clean, and efficient!
    }
}
