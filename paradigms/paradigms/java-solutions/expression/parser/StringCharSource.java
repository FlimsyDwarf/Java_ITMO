package expression.parser;

public class StringCharSource implements CharSource {
	private final String source;
	private int pos;

	public StringCharSource(String source) {
		this.source = source;
		this.pos = 0;
	}

	@Override
	public boolean hasNext() {
		if (pos < source.length()) {
			return true;
		}
		pos++;
		return false;
	}

	@Override
	public char next() {
		return source.charAt(pos++);
	}

	@Override
	public char getBack(int back) {
		pos -= back;
		return pos <= source.length() ? source.charAt(pos - 1) : 0;
	}

	@Override
	public IllegalArgumentException error(String message) {
		return new IllegalArgumentException(pos + ": " + message);
	}
}
