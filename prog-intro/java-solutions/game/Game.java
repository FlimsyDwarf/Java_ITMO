package game;

import java.util.Arrays;

public class Game {
    private final boolean log;
    private final int playersNumber;
    private final Player[] players;


    public Game(final boolean log, final int playersNumber) {
        this.log = log;
        this.playersNumber = playersNumber;
        this.players = new Player[playersNumber];
        Arrays.fill(players, new RandomPlayer());
        players[0] = new CheatingPlayer();
    }

    public Game(final boolean log) {
        this.log = log;
        this.playersNumber = 2;
        this.players = new Player[playersNumber];
        players[0] = new HumanPlayer();
        players[1] = new RandomPlayer();
    }

    public int play(Board board, int player1, int player2) {
        while (true) {
            final int result1 = move(board, players[player1], player1, player2);
            if (result1 != -1) {
                return result1;
            }
            final int result2 = move(board, players[player2], player2, player1);
            if (result2 != -1) {
                return result2;
            }
        }
    }

    public void tournament(int rows, int columns, int toWin) {
        int[][] results = new int[playersNumber][playersNumber];

        for (int i = 0; i < playersNumber; i++) {
            for (int j = i + 1; j < playersNumber; j++) {
                int player1 = i;
                int player2 = j;
                for (int k = 0; k < 2; k++) {
                    int result = play(new TicTacToeBoard(rows, columns, toWin), player1, player2);
                    if (result == player1) {
                        results[player1][player2] += 3;
                    } else if (result == player2) {
                        results[player2][player1] += 3;
                    } else {
                        results[player1][player2]++;
                        results[player2][player1]++;
                    }
                    player1 = j;
                    player2 = i;
                }
                tournamentLog(results);
            }
        }
    }

    private int move(final Board board, final Player player, final int no1, final int no2) {
        try {
            final Move move = player.move(board.getPosition(), board.getCell());
        final Result result = board.makeMove(move);
        log("Player " + (no1 + 1) + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + (no1 + 1) + " won");
            return no1;
        } else if (result == Result.LOSE) {
            log("Player " + (no2 + 1) + " lose");
            return no2;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else {
            return -1;
        }
        } catch (RuntimeException e) {
            log("Player " + (no1 + 1) + " makes Invalid move and loses");
            return no2;
        }
    }

    private void log(final String message) {
        if (log) {
            System.out.println(message);
        }
    }

    private void tournamentLog(int[][] results) {
        StringBuilder sb = new StringBuilder("  ");
        for (int j = 1; j <= playersNumber; j++) {
            sb.append(j + " | ");
        }
        for (int i = 0; i < playersNumber; i++) {
            int total = 0;
            sb.append(System.lineSeparator());
            sb.append((i + 1) + "| ");
            for (int j = 0; j < playersNumber; j++) {
                total += results[i][j];
                if (i == j) {
                    sb.append("#   ");
                } else {
                    sb.append(results[i][j] + "   ");
                }
            }
            sb.append(" total: ").append(total);
        }
        System.out.println(sb);
    }
}
