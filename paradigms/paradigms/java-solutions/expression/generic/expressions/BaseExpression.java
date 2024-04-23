package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public abstract class BaseExpression<T extends Number> implements UltimateExpression<T> {
	final protected Calculator<T> calculator;
	protected BaseExpression(Calculator<T> calculator) {
		this.calculator = calculator;
	}

}
