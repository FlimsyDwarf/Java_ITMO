package expression.exceptions;

import expression.Divide;
import expression.UltimateExpression;

public class CheckedDivide extends Divide {
    public CheckedDivide(UltimateExpression firstVal, UltimateExpression secondVal) {
        super(firstVal, secondVal);
    }

    @Override
    public int getResult(int a, int b) {
        if (b == 0) {
            throw new DivisionByZero();
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new OverflowException();
        }
        return a / b;
    }
}
