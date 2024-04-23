package expression.exceptions;

public class EofException extends IllegalArgumentException {

    public EofException() {
        super();
    }

    @Override
    public String getMessage() {
        return "end of file expected";
    }
}
