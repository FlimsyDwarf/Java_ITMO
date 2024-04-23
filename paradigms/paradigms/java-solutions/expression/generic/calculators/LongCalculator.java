package expression.generic.calculators;

public class LongCalculator implements Calculator<Long>{
    @Override
    public Long add(Long a, Long b) {
        return a + b;
    }

    @Override
    public Long subtract(Long a, Long b) {
        return a - b;
    }

    @Override
    public Long multiply(Long a, Long b) {
        return a * b;
    }

    @Override
    public Long divide(Long a, Long b) {
        return a / b;
    }

    @Override
    public Long negation(Long a) {
        return -a;
    }

    @Override
    public Long mod(Long a, Long b) {
        return a % b;
    }

    @Override
    public Long abs(Long a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }

    @Override
    public Long square(Long a) {
        return a * a;
    }

    @Override
    public Long parseConst(String a) {
        return Long.parseLong(a);
    }

    @Override
    public Long parseConst(int a) {
        return (long) a;
    }
}
