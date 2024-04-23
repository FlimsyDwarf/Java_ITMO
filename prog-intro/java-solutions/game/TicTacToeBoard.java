package game;

import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

public class TicTacToeBoard implements Board {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.',
            Cell.I, '#'
    );

    private final Cell[][] cells;
    private Cell turn;

    private final int[][] moves;

    private final int rows;
    private final int columns;
    private final int toWin;

    private int emptyCells;

    public TicTacToeBoard(final int rows, final int columns, final int toWin) {
        moves = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
        this.rows = rows;
        this.columns = columns;
        this.toWin = toWin;
        this.cells = new Cell[rows][columns]; // :NOTE: don't check arguments => Exception.
        emptyCells = rows * columns;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        for (int i = 0; i < Math.min(rows, columns); i++) {
            cells[i][i] = Cell.I;
            cells[i][Math.min(rows, columns) - i - 1] = Cell.I;
        }
        turn = Cell.X;
    }

    public TicTacToeBoard(final int blocked, final int rows, final int columns, final int toWin) {
        moves = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 } };
        this.rows = rows;
        this.columns = columns;
        this.toWin = toWin;
        this.cells = new Cell[rows][columns];
        emptyCells = rows * columns;
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < blocked; i++) {
            System.out.println("Insert coordinates of " + (i + 1) +  "blocked cell");
            int r = in.nextInt();
            int c = in.nextInt();
            cells[r][c] = Cell.I;
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new CurrentPosition(rows, columns, toWin, turn, cells);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }
        emptyCells--;
        final int moveRow = move.getRow();
        final int moveColumn = move.getColumn();
        cells[moveRow][moveColumn] = move.getValue();
        int previosNumber = 0;
        for (int i = 0; i < moves.length; ++i) {
            int currentNumber = 1;
            for (int j = 1; j <= toWin; j++) {
                int curRow = moveRow + moves[i][0] * j; // :NOTE: why it's not a method
                int curColumn = moveColumn + moves[i][1] * j;
                if (curRow >= rows || curRow < 0
                        || curColumn >= columns || curColumn< 0) {
                    break;
                } else if (turn == cells[curRow][curColumn]) {
                    currentNumber++;
                } else {
                    break;
                }
            }
            if (i % 2 == 0) {
                previosNumber = currentNumber;
            } else if (previosNumber + currentNumber - 1 >= toWin) {
                return Result.WIN;
            } else {
                previosNumber = 0;
            }
        }
        if (emptyCells == 0) {
            return Result.DRAW;
        }
        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < rows
                && 0 <= move.getColumn() && move.getColumn() < columns
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell() && cells[move.getRow()][move.getColumn()] != Cell.I;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("  ");
        for (int i = 0; i < columns; i++) {
            if (i < 10) {
                sb.append(i).append("  ");
            } else {
                sb.append(i).append(' ');
            }
        }
        for (int r = 0; r < rows; r++) {
            sb.append(System.lineSeparator());
            sb.append(r).append(' ');
            for (int c = 0; c < columns; c++) {
                sb.append(SYMBOLS.get(cells[r][c])).append("  ");
            }
        }
        return sb.toString();
    }
}
