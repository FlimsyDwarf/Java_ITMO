package expression;

public class Negation extends BaseUnaryExpression {
	public Negation(UltimateExpression expression) {
		super(expression);
	}

	@Override
	public String getSign() {
		return "-";
	}

	@Override
	public int getPriority() {
		return 3;
	}

	@Override
	public int getResult(int a) {
		return -a;
	}

	@Override
	public double getResult(double a) {
		return -a;
	}
}