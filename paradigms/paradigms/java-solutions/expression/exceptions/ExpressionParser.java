package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.CharSource;
import expression.parser.StringCharSource;

public final class ExpressionParser implements TripleParser {


	@Override
	public UltimateExpression parse(final String expression) {
		return new Parser(new StringCharSource(expression)).parseExpression();
	}

	protected static class Parser extends BaseParser {
		public Parser(final CharSource source) {
			super(source);
		}

		protected UltimateExpression parseExpression() {
			final UltimateExpression result = parseSetClear();
			skipWhiteSpaces();
			if (eof()) {
				return result;
			} else {
				throw new EofException(getPosition());
			}
		}

		private UltimateExpression parseSetClear() {
			skipWhiteSpaces();
			UltimateExpression result = parseSubAdd();
			while (true) {
				skipWhiteSpaces();
				if (test("set")) {
					if (isNotGoodToken()) {
						if (getNext() == END) {
							throw new MissingArgumentException(getPosition());
						} else {
							throw new UnknownCommandException("set" + getWord(), getPosition());
						}
					}
					skipWhiteSpaces();
					result = new Set(result, parseSubAdd());
				} else if (test("clear")) {
					if (isNotGoodToken()) {
						if (getNext() == END) {
							throw new MissingArgumentException(getPosition());
						} else {
							throw new UnknownCommandException("clear" + getWord(), getPosition());
						}
					}
					skipWhiteSpaces();
					result = new Clear(result, parseSubAdd());
				} else {
					return result;
				}
			}
		}

		private UltimateExpression parseSubAdd() {
			skipWhiteSpaces();
			UltimateExpression result = parseDivMul();
			skipWhiteSpaces();
			while (isNext('+') || isNext('-')) {
				if (take('+')) {
					result = new CheckedAdd(result, parseDivMul());
				} else if (take('-')) {
					result = new CheckedSubtract(result, parseDivMul());
				}
				skipWhiteSpaces();
			}
			return result;
		}

		private UltimateExpression parseDivMul() {
			skipWhiteSpaces();
			UltimateExpression result = parseFactor();
			skipWhiteSpaces();
			while (isNext('*') || isNext('/')) {
				if (take('*')) {
					result = new CheckedMultiply(result, parseFactor());
				} else if (take('/')) {
					result = new CheckedDivide(result, parseFactor());
				}
				skipWhiteSpaces();
			}
			return result;
		}

		private UltimateExpression parseFactor() {
			skipWhiteSpaces();
			if (take('-')) {
				if (isDigit()) {
					return new Const(Integer.parseInt(getConst(true)));
				} else {
					return new CheckedNegate(parseFactor());
				}
			} else if (isDigit()) {
				return new Const(Integer.parseInt(getConst(false)));
			} else if (test("pow10")) {
				if (isNotGoodToken()) {
					if (getNext() == END) {
						throw new MissingArgumentException(getPosition());
					} else {
						throw new UnknownCommandException("pow10" + getWord(), getPosition());
					}
				}
				skipWhiteSpaces();
				return new CheckedPow10(parseFactor());
			} else if (test("log10")) {
				if (isNotGoodToken()) {
					if (getNext() == END) {
						throw new MissingArgumentException(getPosition());
					} else {
						throw new UnknownCommandException("log10" + getWord(), getPosition());
					}
				}
				skipWhiteSpaces();
				return new CheckedLog10(parseFactor());
			} else if (test("count")) {
				if (isNotGoodToken()) {
					if (getNext() == END) {
						throw new MissingArgumentException(getPosition());
					} else {
						throw new UnknownCommandException("count" + getWord(), getPosition());
					}
				}
				return new Count(parseFactor());
			} else if (isLetter()) {
				return parseVar();
			} else if (take('(')) {
				final UltimateExpression result = parseSetClear();
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

		private Variable parseVar() {
			// :NOTE: зачем char в скобках?
			return switch (take()) {
				case ('x') -> new Variable("x");
				case ('y') -> new Variable("y");
				case ('z') -> new Variable("z");
				default -> throw new IllegalVariableNameException(getPosition() - 1);
			};
		}

		private boolean isNotGoodToken() {
			return !isNext('-') && !isWhiteSpace() && !isNext('(');
		}

	}
}
