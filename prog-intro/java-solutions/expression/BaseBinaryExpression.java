package expression;

import java.util.Objects;

public abstract class BaseBinaryExpression extends BaseExpression implements BinaryExpression {

    final UltimateExpression firstVal;
    final UltimateExpression secondVal;


    protected BaseBinaryExpression(final UltimateExpression firstVal, final UltimateExpression secondVal) {
        this.firstVal = firstVal;
        this.secondVal = secondVal;
    }

    @Override
    public String toString() {
        return '(' + firstVal.toString() + ' ' + this.getSign() + ' ' + secondVal + ')';
    }
    @Override
    public String toMiniString() {
        StringBuilder result = new StringBuilder();
        final int curPriority = this.getPriority();
        final int firstPriority = firstVal.getPriority();
        final int secondPriority = secondVal.getPriority();

        if (firstPriority >= curPriority) {
            result.append(firstVal.toMiniString());
        } else {
            result.append('(').append(firstVal.toMiniString()).append(')');
        }
        result.append(' ').append(this.getSign()).append(' ');
        if (curPriority < secondPriority || curPriority == secondPriority && this.isCommutative()
                && !(this instanceof Multiply && secondVal instanceof Divide) ) {
            result.append(secondVal.toMiniString());
        } else {
            result.append('(').append(secondVal.toMiniString()).append(')');
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object != null && object.getClass() == this.getClass()) {
            BaseBinaryExpression anotherOp = (BaseBinaryExpression) object;
            return anotherOp.firstVal.equals(this.firstVal)&&
                    anotherOp.secondVal.equals(this.secondVal) &&
                    anotherOp.getSign().equals(this.getSign());
        }
        return false;
    }

    @Override
    public int evaluate(int x) {
        return getResult(firstVal.evaluate(x), secondVal.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return getResult(firstVal.evaluate(x), secondVal.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return getResult(firstVal.evaluate(x, y, z), secondVal.evaluate(x, y, z));
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstVal, secondVal, this.getSign());
    }

}
