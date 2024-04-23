package expression.parser;

import expression.exceptions.MismatchArgumentException;

public class BaseParser {

	protected CharSource source;
	private char ch;
	public static final char END = 0;


	public BaseParser (CharSource source) {
		this.source = source;
		take();
	}

	protected char take() {
		final char result = ch;
		ch = source.hasNext() ? source.next() : END;
		return result;
	}

	protected boolean take(char expected) {
		if (ch == expected) {
			take();
			return true;
		}
		return false;
	}

	protected boolean expect(String expected) {
		for (int i = 0; i < expected.length(); i++) {
			if (ch != expected.charAt(i)) {
				throw new MismatchArgumentException(expected.charAt(i), ch);
			}
			take();
		}
		return true;
	}

	protected boolean isNext(char next) {
		return ch == next;
	}

	protected void skipWhiteSpaces() {
		while (Character.isWhitespace(ch)) {
			take();
		}
	}

	protected boolean eof() {
		return ch == END;
	}

	protected boolean isDigit() {
		return Character.isDigit(ch);
	}

	protected boolean isLetter() {
		return Character.isLetter(ch);
	}

	protected boolean isWhiteSpace() {
		return Character.isWhitespace(ch);
	}

	protected IllegalArgumentException error(String message) {
		return source.error(message);
	}

}
