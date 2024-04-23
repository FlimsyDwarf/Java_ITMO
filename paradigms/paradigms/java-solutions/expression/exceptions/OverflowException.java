package expression.exceptions;

public class OverflowException extends NumberFormatException {
    public OverflowException() {
        super();
    }



    @Override
    public String getMessage() {
        return "overflow";
    }
}
