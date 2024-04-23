package game;

import java.util.Scanner;

public class Utils {
    public static int insertInt(Scanner in) {
        while (true) {
            try {
                return Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("This is not a number. Try again");
            }
        }
    }
}
