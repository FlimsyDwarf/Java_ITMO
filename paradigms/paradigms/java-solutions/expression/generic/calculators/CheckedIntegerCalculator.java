package expression.generic.calculators;

import expression.exceptions.DivisionByZero;
import expression.exceptions.NegativeArgumentException;
import expression.exceptions.OverflowException;

public class CheckedIntegerCalculator implements Calculator<Integer> {
	@Override
	public Integer add(Integer a, Integer b) {
		if ((a > 0 && b > 0 && b > Integer.MAX_VALUE - a) || (a < 0 && b < 0 && b < Integer.MIN_VALUE - a)) {
			throw new OverflowException();
		}
		return a + b;
	}

	@Override
	public Integer subtract(Integer a, Integer b) {
		if (a >= 0 && b < 0 && a - b < 0 ||
				a < 0 && b > 0 && a - b > 0) {
			throw new OverflowException();
		}
		return a - b;
	}

	@Override
	public Integer multiply(Integer a, Integer b) {
		Integer result = a * b;
		if (a != 0 && b != 0 && (a != result / b || b != result / a)) {
			throw new OverflowException();
		}
		return result;
	}

	@Override
	public Integer divide(Integer a, Integer b) {
		if (b == 0) {
			throw new DivisionByZero();
		}
		if (a == Integer.MIN_VALUE && b == -1) {
			throw new OverflowException();
		}
		return a / b;
	}

	@Override
	public Integer mod(Integer a, Integer b) {
		if (b == 0) {
			throw new DivisionByZero();
		}
		return a % b;
	}

	@Override
	public Integer abs(Integer a) {
		if (a == Integer.MIN_VALUE) {
			throw new OverflowException();
		}
		return Math.abs(a);
	}

	@Override
	public Integer square(Integer a) {
		return multiply(a, a);
	}

	@Override
	public Integer parseConst(String a) {
		return Integer.parseInt(a);
	}

	@Override
	public Integer parseConst(int a) {
		return a;
	}

	@Override
	public Integer negation(Integer a) {
		if (a == Integer.MIN_VALUE) {
			throw new OverflowException();
		}
		return -a;
	}

}
