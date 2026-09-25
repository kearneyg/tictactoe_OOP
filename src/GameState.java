public enum GameState {
    X_WON("Вы победили!"),
    O_WON("Компьютер победил!"),
    DRAW("Ничья!"),
    NOT_FINISHED("Игра не закончена");

    private final String message;

    GameState(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}