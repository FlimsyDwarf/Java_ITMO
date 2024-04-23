import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class ReverseOctDec {

	private static int[] expandInt (int[] a) {
		return Arrays.copyOf(a, a.length * 2);
	}

	private static int[][] expandLine(int[][] a) {
		return Arrays.copyOf(a, a.length * 2);
	}

	private static int addEmptyLines(int sz, Scanner scan) {
		sz += scan.lineSkipped();
		scan.resetlineSkipped();
		return sz;
	}

	public static void main(String args[]) {
		int sz = 0;
		int[][] ints = new int[1][1];
		int[] sizes = new int[1];
		try {
			Scanner scan = new Scanner(System.in);
			try {
					int curSz = 0;
					while (scan.hasNextInt() || scan.lineSkipped() > 0) {
						if (scan.lineSkipped() > 0) {
							curSz = 0;
							sz = addEmptyLines(sz, scan);
						}
						while (sz >= ints.length) {
							ints = expandLine(ints);
							sizes = expandInt(sizes);
							curSz = 0;
						}
						if (scan.hasNextInt()) {
							int x = scan.nextInt();
							if (ints[sz] == null) {
								ints[sz] = new int[1];
							}
							if (sizes[sz] == ints[sz].length) {
								ints[sz] = expandInt(ints[sz]);
							}
							ints[sz][curSz] = x;
							curSz += 1;
							sizes[sz] += 1;
						}
					}
			} finally {
				scan.close();
			}
		} catch (IOException e) {
			System.out.println("input error " + e.getMessage());
		}
		for (int i = sz - 1; i >= 0; i--) {
			if (sizes[i] != 0) {
				for (int j = sizes[i] - 1; j >= 0; j--) {
					System.out.print(ints[i][j] + " ");
				}
			}
			//System.out.println();
			System.out.print(System.lineSeparator());
		}
	}
}