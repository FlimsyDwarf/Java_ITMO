package expression.generic.expressions;


import expression.generic.calculators.Calculator;

public abstract class BaseBinaryExpression  <T extends Number> extends BaseExpression<T> implements BinaryExpression<T> {

    final UltimateExpression<T> firstVal;
    final UltimateExpression<T> secondVal;


    protected BaseBinaryExpression (final Calculator<T> calculator, final UltimateExpression<T> firstVal, final UltimateExpression<T> secondVal) {
        super(calculator);
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }


    @Override
    public T evaluate (T x) {
        return getResult(firstVal.evaluate(x), secondVal.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return getResult(firstVal.evaluate(x, y, z), secondVal.evaluate(x, y, z));
    }

}
