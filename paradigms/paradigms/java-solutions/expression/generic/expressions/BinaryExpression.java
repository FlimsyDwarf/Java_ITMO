package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public interface BinaryExpression<T extends Number> extends UltimateExpression<T> {
	T getResult(T a, T b);
}
