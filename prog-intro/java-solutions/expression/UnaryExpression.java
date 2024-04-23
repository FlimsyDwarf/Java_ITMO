package expression;

public interface UnaryExpression extends UltimateExpression {
	int getResult(int a);
	double getResult(double a);

	String getSign();

}
