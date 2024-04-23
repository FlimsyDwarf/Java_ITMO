package game;

import java.util.Map;

public class CurrentPosition implements Position {

    private final int rows;
    private final int columns;
    private final Cell turn;
    private final int toWin;

    private final Cell[][] cells;

    public CurrentPosition(final int rows, final int columns, final int toWin, Cell turn, final Cell[][] cells) {
        this.turn = turn;
        this.rows = rows;
        this.columns = columns;
        this.toWin = toWin;
        this.cells = new Cell[rows][columns];
        System.arraycopy(cells, 0, this.cells, 0, cells.length); // :NOTE: it's a long time and a lot of memory
    }

    @Override
    public boolean isValid(Move move) {
        return 0 <= move.getRow() && move.getRow() < rows
            && 0 <= move.getColumn() && move.getColumn() < columns
            && cells[move.getRow()][move.getColumn()] == Cell.E
            && turn == getCell() && cells[move.getRow()][move.getColumn()] != Cell.I;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

}
