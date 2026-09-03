import java.util.LinkedList;

public class linkedLists {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<>();
        list.add("Apple");
        list.add("Banana");
        list.add("Cherry");
        list.addFirst("Start");
        list.addLast("end");
        list.add(2, "Blueberry");

        System.out.println("First: " + list.getFirst());
        System.out.println("Last: " + list.getLast());

    }
}
