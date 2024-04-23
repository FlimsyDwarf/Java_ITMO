package expression.exceptions;

public class IlligalSymbolException extends IllegalArgumentException {
    final char found;

    IlligalSymbolException(final char found) {
        this.found = found;
    }

    @Override
    public String getMessage() {
        return "found illegal symbol" + found;
    }
}
