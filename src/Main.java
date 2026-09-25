import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Display display = new Display();
        Board board = new Board();
        WinChecker winChecker = new WinChecker();
        Player playerX = new HumanPlayer(scanner, display, CellState.X);
        Player playerO = new BotPlayer(display, CellState.O);

        Game game = new Game(board, display, winChecker, playerX, playerO);

        game.startGameLoop();
    }
}
