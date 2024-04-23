package expression.generic.calculators;

public class DoubleCalculator implements Calculator<Double>{

	@Override
	public Double add(Double a, Double b) {
		return a + b;
	}

	@Override
	public Double subtract(Double a, Double b) {
		return a - b;
	}

	@Override
	public Double multiply(Double a, Double b) {
		return a * b;
	}

	@Override
	public Double divide(Double a, Double b) {
		return a / b;
	}

	@Override
	public Double negation(Double a) {
		return -a;
	}

	@Override
	public Double mod(Double a, Double b) {
		return a % b;
	}

	@Override
	public Double abs(Double a) {
		//System.out.println( "do " + a);
		return Math.abs(a);
	}

	@Override
	public Double square(Double a) {
		return a * a;
	}

	@Override
	public Double parseConst(String a) {
		return Double.parseDouble(a);
	}

	@Override
	public Double parseConst(int a) {
		return (double) a;
	}

}
