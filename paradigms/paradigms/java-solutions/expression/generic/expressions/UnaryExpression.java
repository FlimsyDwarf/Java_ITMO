package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public interface UnaryExpression<T extends Number> extends UltimateExpression<T> {
	T getResult(T a);
}
