package expression;

public class Count extends BaseUnaryExpression {
	public Count(UltimateExpression expression) {
		super(expression);
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public int getResult(int a) {
		return Integer.bitCount(a);
	}

	@Override
	public double getResult(double a) {
		return Integer.bitCount((int) a);
	}

	@Override
	public String getSign() {
		return "count";
	}
}
