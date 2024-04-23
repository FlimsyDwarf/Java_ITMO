package expression.exceptions;

public abstract class BaseException extends RuntimeException {

	final int position;

	protected BaseException(final int position) {
		this.position = position;
	}
}
