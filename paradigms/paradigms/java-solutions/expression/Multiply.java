package expression;

public class Multiply extends BaseBinaryExpression {

    public Multiply (final UltimateExpression firstVal, final UltimateExpression secondVal) {
        super(firstVal, secondVal);
    }

    @Override
    public int getResult(int a, int b) {
        return a * b;
    }

    @Override
    public double getResult(double a, double b) {
        return a * b;
    }


    @Override
    public String getSign() {
        return "*";
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
