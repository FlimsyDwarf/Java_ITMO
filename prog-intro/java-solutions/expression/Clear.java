package expression;

public class Clear extends BaseBinaryExpression {
	public Clear(UltimateExpression leftExpression, UltimateExpression rightExpression) {
		super(leftExpression, rightExpression);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getSign() {
		return "clear";
	}

	@Override
	public int getResult(int a, int b) {
		return a & ~(1 << b);
	}

	@Override
	public double getResult(double a, double b) {
		return (int) a & ~(1 << (int) b);
	}
}
