package expression;

import java.util.Objects;

public class Const implements UltimateExpression {
    Number value;

    public Const(int value) {
        this.value = value;
    }

    public Const(double doubleValue) {
        this.value = doubleValue;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public int getValue() {
        return value.intValue();
    }

    @Override
    public int evaluate(int x) {
        return value.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();

    }
    @Override
    public double evaluate(double x) {
        return (double) value;
    }

    @Override
    public String toMiniString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object anotherVar) {
        if (anotherVar != null && anotherVar.getClass() == this.getClass()) {
            return Objects.equals(((Const) anotherVar).value, this.value);
        }
        return false;
    }

    @Override
    public String getSign() {
        return "";
    }

    @Override
    public int hashCode() {
        return value.hashCode() * 17;
    }

    @Override
    public boolean isCommutative() {
        return false;
    }
    @Override
    public int getPriority() {
        return 3;
    }
}
