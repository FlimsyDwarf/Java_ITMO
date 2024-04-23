package expression.exceptions;

import expression.Const;

import javax.annotation.processing.SupportedSourceVersion;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

	static public void main(String[] args) {
		try {
			while (true) {
				Scanner in = new Scanner(System.in);
				System.out.println("insert expression");
				String expr = in.nextLine();
				System.out.println("1 - evaluate\n2 - toString\n3 - toMiniString");
				int type = in.nextInt();
				try {
					switch (type) {
						case (1) -> {
							System.out.println("insert x");
							int x = insertInt();
							System.out.println("insert y");
							int y = insertInt();
							System.out.println("insert z");
							int z = insertInt();
							System.out.println(new ExpressionParser().parse(expr).evaluate(x, y, z));
						}
						case (2) -> {
							System.out.println(new ExpressionParser().parse(expr).toString());
						}
						case (3) -> {
							System.out.println(new ExpressionParser().parse(expr).toMiniString());
						}
						default -> System.out.println("Try again");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} catch (NoSuchElementException e) {
			System.exit(1);
		}
	}

	private static int insertInt() {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		while (true) {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
				System.out.println("This is not a number\nTry again");
				s = in.nextLine();
			}
		}
	}

}
