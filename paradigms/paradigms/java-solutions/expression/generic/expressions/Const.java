package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Const <T extends Number> implements UltimateExpression<T> {
	final T value;

	public Const(T value) {
		this.value = value;
	}


	@Override
	public T evaluate(T x, T y, T z) {
		return value;
	}

	@Override
	public T evaluate(T x) {
		return value;
	}
}
