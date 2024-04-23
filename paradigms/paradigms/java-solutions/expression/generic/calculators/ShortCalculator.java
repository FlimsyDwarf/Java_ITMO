package expression.generic.calculators;

public class ShortCalculator implements Calculator<Short> {
    @Override
    public Short add(Short a, Short b) {
        int  x = a + b;
        return (short) x;
    }

    @Override
    public Short subtract(Short a, Short b) {
        int x = a - b;
        return (short) x;
    }

    @Override
    public Short multiply(Short a, Short b) {
        int x = a * b;
        return (short) x;
    }

    @Override
    public Short divide(Short a, Short b) {
        int x = a / b;
        return (short) x;
    }

    @Override
    public Short negation(Short a) {
        int x = -a;
        return (short) x;
    }

    @Override
    public Short mod(Short a, Short b) {
        int x = a % b;
        return (short) x;
    }

    @Override
    public Short abs(Short a) {
        if (a < 0) {
            return negation(a);
        }
        return a;
    }

    @Override
    public Short square(Short a) {
        return multiply(a, a);
    }

    @Override
    public Short parseConst(String a) {
        return Short.parseShort(a);
    }

    @Override
    public Short parseConst(int a) {
        return (short) a;
    }
}
