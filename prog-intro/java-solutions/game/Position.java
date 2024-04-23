package game;
public interface Position {
    boolean isValid(Move move);

    Cell getCell();
    int getRows();
    int getColumns();
}
