package expression;

public class Set extends BaseBinaryExpression {

	public Set(UltimateExpression firstVal, UltimateExpression secondVal) {
		super(firstVal, secondVal);
	}

	@Override
	public int getResult(int a, int b) {
		return a | (1 << b);
	}

	@Override
	public double getResult(double a, double b) {
		return (int)a | (1 << (int)b);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String getSign() {
		return "set";
	}
}
