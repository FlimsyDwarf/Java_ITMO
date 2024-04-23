package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Subtract <T extends Number> extends BaseBinaryExpression<T> {

	public Subtract(final Calculator<T> calculator, final UltimateExpression<T> firstVal, final UltimateExpression<T> secondVal) {
		super(calculator, firstVal, secondVal);
	}

	@Override
	public T getResult(T a, T b) {
		return calculator.subtract(a, b);
	}

}
