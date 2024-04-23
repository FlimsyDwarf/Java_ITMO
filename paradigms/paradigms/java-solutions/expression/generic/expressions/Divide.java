package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Divide <T extends Number> extends BaseBinaryExpression<T> {
	public Divide(final Calculator<T> calculator, final UltimateExpression<T> firstVal, final UltimateExpression<T> secondVal) {
		super(calculator, firstVal, secondVal);
	}

	@Override
	public T getResult(T a, T b) {
		return calculator.divide(a, b);
	}

}
