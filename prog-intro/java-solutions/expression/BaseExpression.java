package expression;

public abstract class BaseExpression implements UltimateExpression {
	@Override
	public boolean isCommutative() {
		return this instanceof Multiply || this instanceof Add;
	}
}
