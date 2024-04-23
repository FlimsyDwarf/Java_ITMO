package expression.exceptions;

public class DivisionByZero extends ArithmeticException{
    protected DivisionByZero() {
        super();
    }

    @Override
    public String getMessage() {
        return "division by zero";
    }
}
