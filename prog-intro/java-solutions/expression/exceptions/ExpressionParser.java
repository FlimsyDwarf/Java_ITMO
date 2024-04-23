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
				throw new EofException();
			}
		}

		private UltimateExpression parseSetClear() {
			skipWhiteSpaces();
			UltimateExpression result = parseSubAdd();
			while (true) {
				skipWhiteSpaces();
				if (take('s') && expect("et")) {
					if (isNotGoodToken()) {
						throw new MismatchArgumentException("- or whitespace or (", take());
					}
					skipWhiteSpaces();
					result = new Set(result, parseSubAdd());
				} else if (take('c') && expect("lear")) {
					if (isNotGoodToken()) {
						throw new MismatchArgumentException("- or whitespace or (", take());
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
				// :NOTE: bug
				return new Const(Integer.parseInt(getConst(false)));
			} else if (take('p') && expect("ow10")) {
				if (isNotGoodToken()) {
					throw new MismatchArgumentException("- or whitespace or (", take());
				}
				skipWhiteSpaces();
				return new CheckedPow(parseFactor());
			} else if (take('l') && expect("og10")) {
				if (isNotGoodToken()) {
					throw new MismatchArgumentException("- or whitespace or (", take());
				}
				skipWhiteSpaces();
				return new CheckedLog(parseFactor());
			} else if (take('c') && expect("ount")) {
				if (isNotGoodToken()) {
					throw new MismatchArgumentException("- or whitespace or (", take());
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
			throw new IlligalSymbolException(take());
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
			return switch (take()) {
				case 'x' -> new Variable("x");
				case 'y' -> new Variable("y");
				case 'z' -> new Variable("z");
				default -> throw new IllegalVariableNameException();
			};
		}

		private boolean isNotGoodToken() {
			return !isNext('-') && !isWhiteSpace() && !isNext('(');
		}

	}
}
