package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Square <T extends Number> extends BaseUnaryExpression<T> {
    public Square(Calculator<T> calculator,
                  UltimateExpression<T> first) {
        super(calculator, first);
    }

    @Override
    public T getResult(T a) {
        return calculator.square(a);
    }
}
