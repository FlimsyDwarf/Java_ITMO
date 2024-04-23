package expression.exceptions;

import expression.parser.BaseParser;

public class MissingArgumentException extends BaseException {

    public MissingArgumentException(final int position) {
        super(position);
    }

    @Override
    public String getMessage() {
        return "missing argument at position: " + position;
    }
}
