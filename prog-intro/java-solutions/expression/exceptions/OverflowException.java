package expression.exceptions;

public class OverflowException extends NumberFormatException {
    protected OverflowException() {
        super();
    }

    @Override
    public String getMessage() {
        return "overflow";
    }
}
