package expression.parser;

import expression.*;

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
				throw error("expected end of expression, found: " + take());
			}
		}

		private UltimateExpression parseSetClear() {
//			skipWhiteSpaces();
			UltimateExpression result = parseSubAdd();
			while (true) {
				skipWhiteSpaces();
				// :NOTE: setx
				if (take('s') && expect("et")) {
					if (isNotGoodToken()) {
						throw error("illegal symbol " + take());
					}
					skipWhiteSpaces();
					result = new Set(result, parseSubAdd());
				} else if (take('c') && expect("lear")) {
					if (isNotGoodToken()) {
						throw error("illegal symbol " + take());
					}
					result = new Clear(result, parseSubAdd());
				} else {
					return result;
				}
			}
		}

		private UltimateExpression parseSubAdd() {
			UltimateExpression result = parseDivMul();
			while (true) {
				skipWhiteSpaces();
				if (take('+')) {
					result = new Add(result, parseDivMul());
				} else if (take('-')) {
					result = new Subtract(result, parseDivMul());
				} else {
					return result;
				}
			}
		}

		private UltimateExpression parseDivMul() {
			skipWhiteSpaces();
			UltimateExpression result = parseFactor();
			skipWhiteSpaces();
			while (isNext('*') || isNext('/')) {
				if (take('*')) {
					result = new Multiply(result, parseFactor());
				} else if (take('/')) {
					result = new Divide(result, parseFactor());
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
					return new Negation(parseFactor());
				}
			} else if (isDigit()) {
				// :NOTE: bug
				return new Const(Integer.parseInt(getConst(false)));
			} else if (take('c') && expect("ount")) {
				if (isNotGoodToken()) {
					throw error("illegal symbol " + take());
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
			throw error("incorrect input");
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
			final StringBuilder name = new StringBuilder();
			while (isLetter()) {
				name.append(take());
			}
			return new Variable(name.toString());
		}

		private boolean isNotGoodToken() {
			return !isNext('-') && !isWhiteSpace() && !isNext('(');
		}

	}

	public static void main(final String... args) {
		System.out.println(new ExpressionParser().parse("xset y"));
	}

}
