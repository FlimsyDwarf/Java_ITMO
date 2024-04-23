package expression.generic.calculators;

public interface Calculator<T extends Number> {
	T add(T a, T b);
	T subtract(T a, T b);
	T multiply(T a, T b);
	T divide(T a, T b);
	T negation(T a);
	T mod(T a, T b);
	T abs(T a);
	T square(T a);
	T parseConst(String a);
	T parseConst(int a);

	//T count(T a);
	//T pow10(T a);
	//T log10(T a);
}
