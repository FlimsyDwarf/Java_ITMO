package expression.exceptions;

import expression.BaseUnaryExpression;
import expression.UltimateExpression;

public class CheckedPow10 extends BaseUnaryExpression {

    protected CheckedPow10(UltimateExpression expression) {
        super(expression);
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public int getResult(int a) {
        if (a < 0) {
            throw new NegativeArgumentException();
        }
        int result = 1;
        while (a > 0) {
            // :NOTE: кажется, лучше занести Integer.MAX_VALUE / 10 в константу и не вычислять каждый раз
            if (result <= Integer.MAX_VALUE / 10) {
                result *= 10;
                a--;
            } else {
                throw new OverflowException();
            }
        }
        return result;
    }

    @Override
    public double getResult(double a) {
        return 0;
    }

    @Override
    public String getSign() {
        return "pow10";
    }
}
