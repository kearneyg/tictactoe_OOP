public class Display {
    public void printWelcomeMessage() {
        IO.println("ДОБРО ПОЖАЛОВАТЬ В КРЕСТИКИ-НОЛИКИ\n");
    }

    public void printCoordinatePrompt() {
        System.out.print("Введите два числа (ряд и колонку) через пробел (0-2): ");
    }

    public void printBoard(Board board) {
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                System.out.print("[" + board.getCell(new Coordinate(row, col)).getSymbol() + "]");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printBotPrompt(){
        System.out.print("Компьютер думает...\n");
    }

    public void printGameState(GameState state){
        System.out.printf(state.getMessage());
    }

    public void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
