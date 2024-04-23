package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public interface UltimateExpression<T extends Number>  {
	T evaluate(T x, T y, T z);
	T evaluate(T x);
}
