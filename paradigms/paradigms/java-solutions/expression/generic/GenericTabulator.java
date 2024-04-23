package expression.generic;

import expression.generic.calculators.*;
import expression.generic.expressions.UltimateExpression;
import expression.generic.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {
	@Override
	public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
		Calculator<?> calculator = switch (mode) {
			case "i" -> new CheckedIntegerCalculator();
			case "d" -> new DoubleCalculator();
			case "bi" -> new BigIntegerCalculator();
			case "u" -> new IntegerCalculator();
			case "l" -> new LongCalculator();
			case "s" -> new ShortCalculator();
			default -> throw new IllegalArgumentException("Unknown mode " + mode);
		};
		return solve(calculator, expression, x1, x2, y1, y2, z1, z2);
	}

	public <T extends Number> Object[][][] solve(Calculator<T> calculator,
												 String expression, int x1, int x2, int y1, int y2, int z1, int z2) {

		ExpressionParser<T> parser = new ExpressionParser<>();
		UltimateExpression<T> result = parser.parse(expression, calculator);
		final Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
		for (int i = 0; i <= x2 - x1; i++) {
			for (int j = 0; j <= y2 - y1; j++) {
				for (int k = 0; k <= z2 - z1; k++) {
					try {
						table[i][j][k] = result.evaluate(calculator.parseConst(x1 + i),
								calculator.parseConst(y1 + j), calculator.parseConst(z1 + k));
					} catch (Exception e) {  // :NOTE: ловить конкретные эксепшены
						table[i][j][k] = null;
					}
				}
			}
		}
		return table;
	}
}
