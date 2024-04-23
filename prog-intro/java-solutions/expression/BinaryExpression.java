package expression;

public interface BinaryExpression extends UltimateExpression {
	int getResult(int a, int b);
	double getResult(double a, double b);
}
