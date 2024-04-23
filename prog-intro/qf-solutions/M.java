import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class M {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t = in.nextInt();
        while (t--> 0) {
            int n = in.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = in.nextInt();
            }
            Map<Integer, Integer> m = new HashMap<Integer, Integer>();
            int cnt = 0;
            for (int j = n - 1; j > 0; j--) {
                for (int i = 0; i < j; i++) {
                    Integer cur = m.get(2 * a[j] - a[i]);
                    if (cur != null) {
                        cnt += cur;
                    }
                }
                if (m.get(a[j]) == null) {
                    m.put(a[j], 1);
                } else {
                    m.put(a[j], m.get(a[j]) + 1);
                }
            }
            System.out.println(cnt);
        }
    }
}
