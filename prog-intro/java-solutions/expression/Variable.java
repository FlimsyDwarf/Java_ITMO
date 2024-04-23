package expression;

public class Variable implements UltimateExpression {

    final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public boolean equals(Object anotherVar) {
        if (anotherVar != null && anotherVar.getClass() == this.getClass()) {
            return ((Variable) anotherVar).getName().equals(this.getName());
        }
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> evaluate(x);
            case "y" -> evaluate(y);
            default -> evaluate(z);
        };
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * 17;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getSign() {
        return "";
    }
    @Override
    public boolean isCommutative() {
        return false;
    }
}
