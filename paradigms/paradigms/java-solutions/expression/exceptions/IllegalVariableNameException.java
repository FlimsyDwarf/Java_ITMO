package expression.exceptions;

public class IllegalVariableNameException extends BaseException {

	public IllegalVariableNameException(final int position) {
		super(position);
	}

	@Override
	public String getMessage() {
		return "Variable name is incorrect at position: " + position;
	}
}
