import java.util.Scanner;

public class A {
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		int n = in.nextInt();
		in.close();
		System.out.println( 2 * ((n - a - 1) / (b - a)) + 1);
	}
}