package expression.exceptions;

import expression.Add;
import expression.UltimateExpression;

public class CheckedAdd extends Add {
    public CheckedAdd(UltimateExpression firstVal, UltimateExpression secondVal) {
        super(firstVal, secondVal);
    }

    @Override
    public int getResult(int a, int b) {
        if (a > 0 && b > 0 && a + b < 0 || a < 0 && b < 0 && a + b >= 0) {
            throw new OverflowException();
        }
        return a + b;
    }
}
