
//import java.util.Scanner;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Reverse {

    private static String[] addStr(String[] a, String s) {
        String[] b = new String[a.length * 2];
        b[a.length] = s;
        System.arraycopy(a, 0, b, 0, a.length);
        return b;
    }

    private static int[] expandInt(int[] a) {
        return Arrays.copyOf(a, a.length * 2);
    }

    private static int[][] expandLine(int[][] a) {
        return Arrays.copyOf(a, a.length * 2);
    }

    public static void main(String args[]) throws IOException {
        Scanner scan = new Scanner(System.in);
        boolean isNotNullLine = false;
        int sz = 0;
        int[][] ints = new int[1][1];
        int[] sizes = new int[1];
        int curSz = 0;
        boolean toAdd = false;
        while (scan.hasNextInt()) {
            toAdd = true;
            if (scan.lineSkipped() > 0) {
                curSz = 0;
                sz += scan.lineSkipped();
                scan.resetlineSkipped();
            }
            int x = scan.nextInt();
            while (sz >= ints.length) {
                ints = expandLine(ints);
                sizes = expandInt(sizes);
                curSz = 0;
            }
            if (ints[sz] == null) {
                ints[sz] = new int[1];
            }
            if (sizes[sz] == ints[sz].length) {
                ints[sz] = expandInt(ints[sz]);
            }
            ints[sz][curSz] = x;
            curSz += 1;
            sizes[sz] += 1;
            if (sizes[sz] > 0) {
                isNotNullLine  = true;
            }
        }
        if (toAdd) {
            sz++;
        }
        if (scan.lineSkipped() > 1) {
            curSz = 0;
            if (isNotNullLine) {
                sz -= 1;
            }
            sz += scan.lineSkipped();
            scan.resetlineSkipped();
        }
        while (sz >= ints.length) {
            ints = expandLine(ints);
            sizes = expandInt(sizes);
            curSz = 0;
        }
        scan.close();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("output.txt"), StandardCharsets.UTF_8));
        for (int i = sz - 1; i >= 0; i--) {
            if (sizes[i] != 0) {
                for (int j = sizes[i] - 1; j >= 0; j--) {
                    writer.write(ints[i][j] + " ");
                    System.out.print(ints[i][j] + " ");
                }
            }
            writer.write(System.lineSeparator());
            System.out.print(System.lineSeparator());
            //System.out.println();
        }
        writer.close();
    }
}