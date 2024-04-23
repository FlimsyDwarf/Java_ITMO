package expression;

public class Subtract extends BaseBinaryExpression {

    public Subtract(final UltimateExpression firstVal, final UltimateExpression secondVal) {
        super(firstVal, secondVal);
    }

    @Override
    public int getResult(int a, int b) {
        return a - b;
    }

    @Override
    public double getResult(double a, double b) {
        return a - b;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getSign() {
        return "-";
    }
}
