import java.util.Random;

public class BotPlayer implements Player{

    private static final int THINKING_DELAY_MS = 1000;
    private final Random random = new Random();
    private final Display display;
    private final CellState symbol;

    public BotPlayer(Display display, CellState symbol) {
        this.display = display;
        this.symbol = symbol;
    }

    @Override
    public CellState getSymbol() {
        return symbol;
    }

    @Override
    public Coordinate makeMove(Board board){
        display.printBotPrompt();
        pause();
        return findRandomEmptyCell(board);
    }

    private void pause() {
        try {
            Thread.sleep(THINKING_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Coordinate findRandomEmptyCell(Board board) {
        do {
            int row = random.nextInt(board.getBoardSize());
            int col = random.nextInt(board.getBoardSize());
            Coordinate coord = new Coordinate(row, col);

            if (board.getCell(coord) == CellState.EMPTY) {
                return coord;
            }
        } while (true);
    }
}
