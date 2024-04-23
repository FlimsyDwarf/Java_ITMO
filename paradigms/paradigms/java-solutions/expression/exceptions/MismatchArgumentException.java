package expression.exceptions;

public class MismatchArgumentException extends BaseException {

    final String expected;
    final char found;

    public MismatchArgumentException(final String expected, final char found, final int position) {
        super(position);
        this.expected = expected;
        this.found = found;
    }

    public MismatchArgumentException(final char expected, final char found, final int position) {
        super(position);
        this.expected = Character.toString(expected);
        this.found = found;
    }

    @Override
    public String getMessage() {
        return "expected " + expected + " found " + found + " at position: " + position;
    }
}
