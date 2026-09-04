// Enumeration -- legacy (Java 1.0), for Vector and Hashtable.
// Good for: nothing new. Only old APIs that still hand one back.
// Bad at:   no remove(); Vector/Hashtable synchronize every call. Use Iterator.
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class EnumerationIteration {
    public static void main(String[] args) {
        Vector<String> vector = new Vector<>();
        vector.add("Ann");
        vector.add("Ben");
        vector.add("Cal");

        Enumeration<String> e = vector.elements();
        while (e.hasMoreElements()) {
            System.out.print(e.nextElement() + " ");
        }
        System.out.println();

        Hashtable<String, Integer> table = new Hashtable<>();
        table.put("Ann", 34);
        table.put("Ben", 28);
        Enumeration<String> keys = table.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            System.out.println(key + " -> " + table.get(key));
        }

        // no remove() -- read-only traversal, unlike Iterator
        Iterator<String> it = vector.iterator();
        while (it.hasNext()) {
            if (it.next().equals("Ben")) {
                it.remove();
            }
        }
        System.out.println(vector);   // [Ann, Cal]
    }
}
