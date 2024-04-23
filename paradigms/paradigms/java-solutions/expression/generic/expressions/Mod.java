package expression.generic.expressions;

import expression.generic.calculators.Calculator;

public class Mod <T extends Number> extends BaseBinaryExpression<T> {

    public Mod(Calculator<T> calculator,
               UltimateExpression<T> firstVal, UltimateExpression<T> secondVal) {
        super(calculator, firstVal, secondVal);
    }

    @Override
    public T getResult(T a, T b) {
        return calculator.mod(a, b);
    }
}
