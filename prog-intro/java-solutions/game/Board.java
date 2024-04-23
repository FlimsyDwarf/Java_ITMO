package game;

public interface Board {
    Position getPosition();
    Cell getCell();
    boolean isValid(Move move);
    Result makeMove(Move move);
}
