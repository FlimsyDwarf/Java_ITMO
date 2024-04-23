package expression.exceptions;

public class IllegalVariableNameException extends IllegalArgumentException {
	public IllegalVariableNameException() {
		super();
	}

	@Override
	public String getMessage() {
		return "Variable name is incorrect";
	}
}
