package expression.exceptions;

public class DivisionByZero extends ArithmeticException{
    public DivisionByZero() {
        super();
    }

    @Override
    public String getMessage() {
        return "division by zero";
    }
}
