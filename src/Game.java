public class Game {
    private final Board board;
    private final Display display;
    private final WinChecker winChecker;
    private final Player playerX;
    private final Player playerO;

    public Game(Board board, Display display, WinChecker winChecker, Player playerX, Player playerO) {
        this.board = board;
        this.display = display;
        this.winChecker = winChecker;
        this.playerX = playerX;
        this.playerO = playerO;
    }

    public void startGameLoop() {
        display.printWelcomeMessage();
        display.printBoard(board);

        boolean isPlayerXTurn = true;

        do {
            Player current = isPlayerXTurn ? playerX : playerO;

            board.setCell(current.makeMove(board), current.getSymbol());
            display.clearConsole();
            display.printBoard(board);
            isPlayerXTurn = !isPlayerXTurn;

            GameState currentState = winChecker.checkGameState(board);
            if (currentState != GameState.NOT_FINISHED) {
                display.printGameState(currentState);
                return;
            }
        } while (true);
    }
}