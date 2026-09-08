import java.io.FileReader;
import java.io.IOException;


public class Throws {
    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader("data.txt");
        int ch = fr.read();
        System.out.println((char) ch);
        fr.close();
    }
}

