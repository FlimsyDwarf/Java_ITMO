package expression.generic.calculators;

public class IntegerCalculator implements Calculator<Integer>{
    @Override
    public Integer add(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        return a / b;
    }

    @Override
    public Integer negation(Integer a) {
        return -a;
    }

    @Override
    public Integer mod(Integer a, Integer b) {
        return a % b;
    }

    @Override
    public Integer abs(Integer a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }

    @Override
    public Integer square(Integer a) {
        return a * a;
    }

    @Override
    public Integer parseConst(String a) {
        return Integer.parseInt(a);
    }

    @Override
    public Integer parseConst(int a) {
        return a;
    }
}
