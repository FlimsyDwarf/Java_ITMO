package expression.exceptions;

public class UnknownCommandException extends BaseException {
	final String found;

	public UnknownCommandException(final String found, final int position) {
		super(position);
		this.found = found;
	}

	@Override
	public String getMessage() {
		return "Unknown command " + found + " at postion: " + position;
	}
}
