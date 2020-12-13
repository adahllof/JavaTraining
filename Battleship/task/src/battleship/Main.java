package battleship;
import java.util.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        GameBoard player1board = new GameBoard(10);
        GameBoard player2board = new GameBoard(10);

        System.out.println("Player 1, place your ships on the game field");
        player1board.placeShips();
        swapPlayers();

        System.out.println("Player 2, place your ships on the game field");
        player2board.placeShips();
        swapPlayers();

        boolean gameOver;
        do {
            player2board.printBoard(true, true);
            player1board.printBoard(false, false);
            System.out.println("Player 1, it's your turn:");
            gameOver = player1board.fireAtEnemy(player2board);
            if (gameOver) break;
            else swapPlayers();

            player1board.printBoard(true, true);
            player2board.printBoard(false, false);
            System.out.println("Player 2, it's your turn:");
            gameOver = player2board.fireAtEnemy(player1board);
            if (gameOver) break;
            else swapPlayers();
        } while (true);
    }
    private static void  swapPlayers() {
        boolean ok = false;
        while (!ok) {
            ok = promptEnterKey();
        }
        clearScreen();

    }
    public static boolean promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            byte[] buffer = new byte[2];
            int read = System.in.read(buffer);
            if (read == 1) {
                return bytes2char(buffer)[0] == '\n' || bytes2char(buffer)[0] == '\r';
            } else if (read == 2) {
                return bytes2char(buffer)[0] == '\r'&& bytes2char(buffer)[1] == '\n';
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
        private static char[] bytes2char(byte[] bytes) {
            int i = 0;
            char[] c = new char[bytes.length];
            for (byte b : bytes) {
                c[i] = (char) (b & 0xFF);
                i++;
            }
            return c;
        }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
