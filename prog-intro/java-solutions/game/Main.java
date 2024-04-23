package game;

import java.util.NoSuchElementException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) { // :NOTE: I can't play your game. log in NOTE.md
        try {
            Scanner in = new Scanner(System.in);
            int numberOfPlayers;
            int columns;
            int rows;
            int toWin;
            while (true) {
                System.out.println("Insert number of players: ");
                numberOfPlayers = Utils.insertInt(in);
                System.out.println("Insert number of rows: ");
                rows = Utils.insertInt(in);
                System.out.println("Insert number of columns: ");
                columns = Utils.insertInt(in);
                System.out.println("Insert number to win: ");
                toWin = Utils.insertInt(in);
                if (rows * columns < 1 || numberOfPlayers < 2) {
                    System.out.println("Values are incorrect. Try again");
                } else {
                    break;
                }
            }
            final Game game = new Game(true, numberOfPlayers);
            game.tournament(rows, columns, toWin);
        } catch (NoSuchElementException e) { // :NOTE: it's not a solution for this problem
            System.out.println("THE GAME IS CLOSED");
        }
    }
}
