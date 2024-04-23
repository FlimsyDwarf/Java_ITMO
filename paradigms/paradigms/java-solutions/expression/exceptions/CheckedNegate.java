package expression.exceptions;

import expression.Negation;
import expression.UltimateExpression;

public class CheckedNegate extends Negation {
    public CheckedNegate(UltimateExpression expression) {
        super(expression);
    }

    @Override
    public int getResult(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new OverflowException();
        }
        return -a;
    }
}
