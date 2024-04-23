package expression.generic.parser;

import expression.exceptions.EofException;
import expression.exceptions.MissingArgumentException;
import expression.exceptions.IllegalVariableNameException;
import expression.generic.calculators.Calculator;
import expression.generic.expressions.*;
import expression.parser.CharSource;
import expression.parser.StringCharSource;

public final class ExpressionParser <T extends Number> {
	public UltimateExpression<T> parse(final String expression, final Calculator<T> calculator) {
		return new Parser<T>(new StringCharSource(expression), calculator).parseExpression();
	}

	protected static class  Parser <T extends Number> extends BaseParser<T> {
		Calculator<T> calculator;
		public Parser(final CharSource source, Calculator<T> calculator) {
			super(source);
			this.calculator = calculator;
		}

		protected UltimateExpression<T> parseExpression() {
			final UltimateExpression<T> result = parseSubAdd();
			skipWhiteSpaces();
			if (eof()) {
				return result;
			} else {
				throw new EofException(getPosition());
			}
		}

		private UltimateExpression<T> parseSubAdd() {
			skipWhiteSpaces();
			UltimateExpression<T> result = parseDivMul();
			skipWhiteSpaces();
			while (test('+') || test('-')) {
				if (take('+')) {
					// :NOTE: тут можно не прописывать T в явном виде, просто <>
					result = new Add<T>(calculator, result, parseDivMul());
				} else if (take('-')) {
					result = new Subtract<T>(calculator, result, parseDivMul());
				}
				skipWhiteSpaces();
			}
			return result;
		}

		private UltimateExpression<T> parseDivMul() {
			skipWhiteSpaces();
			UltimateExpression<T> result = parseFactor();
			skipWhiteSpaces();
			while (test('*') || test('/') || test("mod")) {
				if (take('*')) {
					result = new Multiply<T>(calculator, result, parseFactor());
				} else if (take('/')) {
					result = new Divide<T>(calculator, result, parseFactor());
				} else if (take("mod")) {
					result = new Mod<T>(calculator, result, parseFactor());
				}
				skipWhiteSpaces();
			}
			return result;
		}

		private UltimateExpression<T> parseFactor() {
			skipWhiteSpaces();
			if (take('-')) {
				if (isDigit()) {
					return new Const<T>(calculator.parseConst(getConst(true)));
				} else {
					return new Negation<T>(calculator, parseFactor());
				}
			} else if (isDigit()) {
				return new Const<T>(calculator.parseConst(getConst(false)));
			} else if (take("square")) {
				return new Square<T>(calculator, parseFactor());
			} else if (take("abs")) {
				return new Abs<T>(calculator, parseFactor());
			}
			else if (isLetter()) {
				return parseVar();
			} else if (take('(')) {
				final UltimateExpression<T> result = parseSubAdd();
				skipWhiteSpaces();
				expect(")");
				return result;
			}
			throw new MissingArgumentException(getPosition());
		}

		private String getConst(boolean negative) {
			final StringBuilder result = new StringBuilder();
			if (negative) {
				result.append('-');
			}
			while (isDigit()) {
				result.append(take());
			}
			return result.toString();
		}

		private Variable<T> parseVar() {
			return switch (take()) {
				case 'x' -> new Variable<T>("x");
				case 'y' -> new Variable<T>("y");
				case 'z' -> new Variable<T>("z");
				default -> throw new IllegalVariableNameException(getPosition() - 1);
			};
		}

	}
}
