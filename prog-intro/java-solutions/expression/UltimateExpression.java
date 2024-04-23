package expression;

public interface UltimateExpression extends Expression, TripleExpression, DoubleExpression, ToMiniString {
	int getPriority();
	boolean isCommutative();

	String getSign();

}
