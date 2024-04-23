import java.util.Scanner;

public class J {
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		in.nextLine();
		int[][] a = new int[n][n];
		for (int i = 0; i < n; i++) {
			String cur = in.nextLine();
			for (int j = 0; j < n; j++) {
				a[i][j] = Character.getNumericValue(cur.charAt(j));
			}
		}
		in.close();

		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				if (a[i][j] == 0) {
					continue;
				}
				for (int k = j + 1; k < n; k++) {
					a[i][k] = (a[i][k] - a[j][k] + 10) % 10;
				}
			}
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j]);
			}
			System.out.println();
		}
	}
}
