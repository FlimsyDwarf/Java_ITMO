package expression;

public abstract class BaseUnaryExpression extends BaseExpression implements UnaryExpression {
	UltimateExpression expression;

	protected BaseUnaryExpression(UltimateExpression expression) {
		this.expression = expression;
	}

	@Override
	public String toString() {
		return this.getSign()  + '(' + expression.toString() + ')';
	}

	@Override
	public String toMiniString() {
		StringBuilder result = new StringBuilder();
		final int curPriority = this.getPriority();
		final int expressionPriority = expression.getPriority();
		final String curSign = this.getSign();
		if (curPriority > expressionPriority) {
			result.append(curSign).append('(').append(expression.toMiniString()).append(')');
		} else {
			result.append(curSign).append(' ').append(expression.toMiniString());
		}
		return result.toString();
	}

	@Override
	public boolean equals(Object object) {
		if (object != null && object.getClass() == this.getClass()) {
			BaseUnaryExpression anotherExp = (BaseUnaryExpression) object;
			return anotherExp.expression.equals(this.expression)&&
					anotherExp.getSign().equals(this.getSign());
		}
		return false;
	}

	@Override
	public double evaluate(double x) {
		return getResult(expression.evaluate(x));
	}

	@Override
	public int evaluate(int x) {
		return getResult(expression.evaluate(x));
	}

	@Override
	public int evaluate(int x, int y, int z) {
		return getResult(expression.evaluate(x, y, z));
	}
}
