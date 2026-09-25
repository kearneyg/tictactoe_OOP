public enum CellState {
    EMPTY(0, ' '), X(1, 'X'), O(-1, 'O');

    private final int value;
    private final char symbol;

    CellState(int value, char symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() {
        return value;
    }

    public char getSymbol() {
        return symbol;
    }
}