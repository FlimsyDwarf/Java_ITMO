package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public abstract class BaseUnaryExpression <T extends Number> extends BaseExpression<T> implements UnaryExpression<T> {
	UltimateExpression<T> expression;

	protected BaseUnaryExpression(final Calculator<T> calculator, UltimateExpression<T> expression) {
		super(calculator);
		this.expression = expression;
	}

	@Override
	public T evaluate(T x, T y, T z) {
		return getResult(expression.evaluate(x, y, z));
	}

	@Override
	public T evaluate(T x) {
		return getResult(expression.evaluate(x));
	}
}
