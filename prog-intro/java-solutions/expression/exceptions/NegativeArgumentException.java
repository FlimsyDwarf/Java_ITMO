package expression.exceptions;

public class NegativeArgumentException extends NumberFormatException {

    public NegativeArgumentException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Positive argument was expected";
    }
}
