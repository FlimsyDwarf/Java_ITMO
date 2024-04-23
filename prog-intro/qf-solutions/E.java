import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.Scanner;


public class E {
	public static int[] addInt(int[] a, int x) {
		int sz = a[0];
		if (sz + 1 == a.length) {
			a =  Arrays.copyOf(a, a.length * 2);
			a[sz + 1] = x;
		} else {
			a[sz + 1] = x;
		}
		a[0]++;
		return a;
	}

	public static int[] bfs(int s, int[][] gr, int p[],int n) {
		boolean[] usd = new boolean[n];
		//int[] q = new int[n];
		int[] d = new int[n];
		int cur = 0;
		int sz = 1;
		//q[cur] = s;
		p[s] = -1;
		usd[s] = true;
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(s);
		while (!q.isEmpty()) {
			int v = q.poll();
			for (int i = 1; i <= gr[v][0]; i++) {
				int u = gr[v][i];
				if (!usd[u]) {
					usd[u] = true;
					d[u] = d[v] + 1;
					p[u] = v;
					q.offer(u);
				}
			}
			cur++;
		}
		return d;
	}

	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		int n, m;
		n = in.nextInt();
		m = in.nextInt();
		int[][] gr = new int[n][1];
		for (int i = 1; i < n; i++) {
			int v, u;
			v = in.nextInt() - 1;
			u = in.nextInt() - 1;
			gr[v] = addInt(gr[v], u);
			gr[u] = addInt(gr[u], v);
		}
		int[] c = new int[m];
		for (int i = 0; i < m; i++) {
			c[i] = in.nextInt() - 1;
		}
		int[] p = new int[n];
		int [] d = bfs(c[0], gr, p, n);
		int f = c[0];
		for (int i = 1; i < m; ++i) {
			if (d[c[i]] > d[f]) {
				f = c[i];
			}
		}
		int mx = d[f];
		if (mx % 2 != 0) {
			System.out.println("NO\n");
			return;
		}
		for (int v = f; v != -1; v = p[v]) {
			if (d[v] == mx / 2) {
				f = v;
				break;
			}
		}
		d = bfs(f, gr, p, n);
		int dist = d[c[0]];
		boolean can = true;
		for (int i = 0; i < m; i++) {
			if (d[c[i]] != dist) {
				System.out.println("NO\n");
				return;
			}
		}
		System.out.println("YES\n" + (f + 1));
	}
}
