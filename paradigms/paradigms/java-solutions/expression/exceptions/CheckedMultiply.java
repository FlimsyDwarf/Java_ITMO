package expression.exceptions;

import expression.Multiply;
import expression.UltimateExpression;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(UltimateExpression firstVal, UltimateExpression secondVal) {
        super(firstVal, secondVal);
    }

    @Override
    public int getResult(int a, int b) {
        int result = a * b;
        if (a != 0 && b != 0 && (a != result / b || b != result / a)) {
            throw new OverflowException();
        }
        return result;
    }
}
