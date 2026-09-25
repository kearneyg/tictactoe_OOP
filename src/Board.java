public class Board {

    private final CellState[][] board;
    private final int BOARD_SIZE = 3;

    /* Board setter */
    public Board() {
        board = new CellState[BOARD_SIZE][BOARD_SIZE];
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = CellState.EMPTY;
            }
        }
    }

    public CellState getCell(Coordinate coord){
        return board[coord.row()][coord.col()];
    }

    public void setCell(Coordinate coord, CellState value){
        board[coord.row()][coord.col()] = value;
    }

    public boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == CellState.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getBoardSize(){
        return BOARD_SIZE;
    }

}
