package md2html;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Md2Html {

    public static void main(String[] args) throws IOException {
        try {
            Parser a = new Parser(args[0], args[1]);
            a.parse();
        } catch (FileNotFoundException e) {
            System.out.println("Problem with files" + e.getMessage());
        } catch (IOException e) {
            System.out.println("Something went wrong" + e.getMessage());
        }
    }
}