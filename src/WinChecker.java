import java.util.ArrayList;
import java.util.List;

public class WinChecker {

    public GameState checkGameState(Board board) {
        List<Integer> sums = new ArrayList<>();

        sums.addAll(getRowSums(board));
        sums.addAll(getColumnSums(board));
        sums.addAll(getDiagonalSums(board));

        return determineOutcome(sums, board);
    }

    private List<Integer> getRowSums(Board board) {
        List<Integer> rowSums = new ArrayList<>();
        for (int row = 0; row < board.getBoardSize(); row++) {
            int sum = 0;
            for (int col = 0; col < board.getBoardSize(); col++) {
                sum += board.getCell(new Coordinate(row, col)).getValue();
            }
            rowSums.add(sum);
        }
        return rowSums;
    }

    private List<Integer> getColumnSums(Board board) {
        List<Integer> colSums = new ArrayList<>();
        for (int col = 0; col < board.getBoardSize(); col++) {
            int sum = 0;
            for (int row = 0; row < board.getBoardSize(); row++) {
                sum += board.getCell(new Coordinate(row, col)).getValue();
            }
            colSums.add(sum);
        }
        return colSums;
    }

    private List<Integer> getDiagonalSums(Board board) {
        int leftDiagonal = 0;
        int rightDiagonal = 0;
        for (int i = 0; i < board.getBoardSize(); i++) {
            leftDiagonal += board.getCell(new Coordinate(i, i)).getValue();
            rightDiagonal += board.getCell(new Coordinate(i, board.getBoardSize() - 1 - i)).getValue();
        }
        return List.of(leftDiagonal, rightDiagonal);
    }

    private GameState determineOutcome(List<Integer> sums, Board board) {
        if (sums.contains(board.getBoardSize())) {
            return GameState.X_WON;
        } else if (sums.contains(-board.getBoardSize())) {
            return GameState.O_WON;
        } else if (board.isBoardFull()) {
            return GameState.DRAW;
        } else {
            return GameState.NOT_FINISHED;
        }
    }
}
