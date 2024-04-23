package expression.parser;

import expression.exceptions.MismatchArgumentException;

public class BaseParser {

	protected CharSource source;
	private int position = 0;
	private char ch;
	public static final char END = 0;


	public BaseParser (CharSource source) {
		this.source = source;
		take();
	}

	protected char take() {
		final char result = ch;
		ch = source.hasNext() ? source.next() : END;
		position++;
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
				throw new MismatchArgumentException(expected.charAt(i), ch, getPosition());
			}
			take();
		}
		return true;
	}

	protected boolean test(String test) {
		for (int i = 0; i < test.length(); i++) {
			if (test.charAt(i) != ch) {
				ch = source.getBack(i);
				return false;
			}
			take();
		}
		return true;
	}

	protected String getWord() {
		skipWhiteSpaces();
		StringBuilder str = new StringBuilder();
		while (isLetter() || isDigit() && ch != END) {
			str.append(ch);
			take();
		}
		return str.toString();
	}

	protected boolean isNext(char next) {
		return ch == next;
	}

	protected void skipWhiteSpaces() {
		while (Character.isWhitespace(ch)) {
			take();
		}
	}
	protected int getPosition() {
		return position++;
	}
	protected char getNext() {
		return ch;
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
