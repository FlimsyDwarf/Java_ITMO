package expression.exceptions;

public class EofException extends BaseException {


    public EofException(final int position) {
        super(position);
    }

    @Override
    public String getMessage() {
        return "end of file expected at position: " + position;
    }
}
