package expression.generic.expressions;

public class Variable <T extends Number> implements UltimateExpression<T> {

	final String name;

	public Variable(String name) {
		this.name = name;
	}

	@Override
	public T evaluate(T x, T y, T z) {
		return switch (name) {
			case "x" -> x;
			case "y" -> y;
			default -> z;
		};
	}

	@Override
	public T evaluate(T x) {
		return x;
	}

}
