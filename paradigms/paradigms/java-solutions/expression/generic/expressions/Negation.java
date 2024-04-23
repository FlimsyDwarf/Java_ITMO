package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Negation <T extends Number> extends BaseUnaryExpression<T> {

	public Negation(final Calculator<T> calculator, final UltimateExpression<T> expression) {
		super(calculator, expression);
	}

	@Override
	public T getResult(T a) {
		return calculator.negation(a);
	}


}
