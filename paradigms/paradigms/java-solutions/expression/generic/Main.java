package expression.generic;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GenericTabulator tabulator = new GenericTabulator();
        Object[][][] table;
        while (true) {
            try {
                Scanner in = new Scanner(System.in);
                System.out.println("Insert modification");
                String mod = in.nextLine();
                System.out.println("Insert expression");
                String expression = in.nextLine();
                table = tabulator.tabulate(mod, expression, -2, 2,
                        -2, 2,
                        -2, 2);
                System.out.println("x y z result");
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        for (int k = 0; k < 5; k++) {
                            System.out.format("%d %d %d %s\n",
                                    i - 2, j - 2, k - 2, table[i][j][k]);
                        }
                    }
                }

            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                System.exit(-1);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
