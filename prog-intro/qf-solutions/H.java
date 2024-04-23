import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class H {

	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		int[] a = new int[n];
		int[] p = new int[n];
		int mx = 0;
		for (int i = 0; i < n; i++) {
			a[i] = in.nextInt();
			p[i] = (i == 0 ? a[i] : p[i - 1] + a[i]);
			mx = Math.max(mx ,a[i]);
		}
		int q = in.nextInt();
		Map<Integer, Integer> usd = new HashMap<>();
		while (q --> 0) {
			int t = in.nextInt();
			if (t < mx) {
				System.out.println("Impossible");
			} else if (usd.get(t) != null) {
				System.out.println(usd.get(t));
			} else {
				int cnt = 0;
				int b = 0;
				for (int i = 0; i < n; i++) {
					int l = i;
					int r = n;
					while (r - l > 1) {
						int m = (r + l) / 2;
						if (p[m] - b > t) {
							r = m;
						} else {
							l = m;
						}
					}
					b = p[l];
					i = l;
					cnt++;
				}
				usd.put(t, cnt);
				System.out.println(cnt);
			}
		}
		in.close();
	}
}
