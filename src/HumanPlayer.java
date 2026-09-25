import java.util.Scanner;

public class HumanPlayer implements Player {

    private final Scanner scanner;
    private final Display display;
    private final CellState symbol;

    public HumanPlayer(Scanner scanner, Display display, CellState symbol) {
        this.scanner = scanner;
        this.display = display;
        this.symbol = symbol;
    }

    @Override
    public CellState getSymbol() {
        return symbol;
    }

    @Override
    public Coordinate makeMove(Board board) {
        display.printCoordinatePrompt();

        do {
            String[] input = scanner.nextLine().split(" ");
            // допущения - не проверяем на наличие пробела и корректность цифр
            int row = Integer.parseInt(input[0]);
            int col = Integer.parseInt(input[1]);
            Coordinate coord = new Coordinate(row, col);

            if (isValidMove(coord, board)){
                return coord;
            }
        } while (true);
    }

    private boolean isValidMove(Coordinate coord, Board board){
        if (coord.row() < 0 || coord.row() >= board.getBoardSize() || coord.col() < 0 || coord.col() >= board.getBoardSize()) {
            display.printError("Неверный ввод. Введите два значения от 0-2 через пробел: ");
            return false;
        } else if (board.getCell(coord) != CellState.EMPTY) {
            display.printError("Клетка заполнена. Выберите другую клетку: ");
            return false;
        }
        return true;
    }
}
