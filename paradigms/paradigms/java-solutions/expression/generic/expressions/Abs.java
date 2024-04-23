package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Abs <T extends Number> extends BaseUnaryExpression<T> {
    public Abs(Calculator<T> calculator, UltimateExpression<T> first) {
        super(calculator, first);
    }

    @Override
    public T getResult(T a) {
        return calculator.abs(a);
    }
}
