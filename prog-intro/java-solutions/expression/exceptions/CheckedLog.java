package expression.exceptions;

import expression.BaseUnaryExpression;
import expression.UltimateExpression;

public class CheckedLog extends BaseUnaryExpression {
    public CheckedLog(UltimateExpression expression) {
        super(expression);
    }

    @Override
    public int getResult(int a) {
        if (a <= 0) {
            throw new NegativeArgumentException();
        }
        int result = 0;
        while (a >= 10) {
            a /= 10;
            result++;
        }
        return result;
    }

    @Override
    public double getResult(double a) {
        return 0;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getSign() {
        return "log10";
    }
}
