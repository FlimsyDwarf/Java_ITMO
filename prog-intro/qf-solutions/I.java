import java.util.Scanner;

public class I {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        final int inf = 2147483647;
        int n = in.nextInt();
        int[][] a = new int[n][3];
        int xl = inf, xr = -inf, yl = inf, yr = -inf;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = in.nextInt();
            }
            xl = Math.min(xl, a[i][0] - a[i][2]);
            xr = Math.max(xr, a[i][0] + a[i][2]);
            yl = Math.min(yl, a[i][1] - a[i][2]);
            yr = Math.max(yr, a[i][1] + a[i][2]);
        }
        int x = (xl + xr) / 2;
        int y = (yl + yr) / 2;
        int h =  (Math.max(xr - xl, yr - yl) + 1) / 2;
        System.out.println(x + " " + y + " " + h);
    }

}
