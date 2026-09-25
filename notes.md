# Classes

## Board
**Responsibility:** owns the grid. The only class allowed to read or write individual cell values — nothing else ever touches the underlying array.

**Fields**
- `private final CellState[][] board`
- `private final int BOARD_SIZE = 3`

**Constructor**
- `Board()` — allocates the grid and fills every cell with `CellState.EMPTY`.

**Methods**
- `CellState getCell(Coordinate coord)` — returns the value stored at a position.
- `void setCell(Coordinate coord, CellState value)` — writes a value at a position. Trusts the caller to have already validated the coordinate (that validation lives in `HumanPlayer`, not here).
- `boolean isBoardFull()` — true once no cell is `EMPTY`.
- `int getBoardSize()` — exposes `BOARD_SIZE` so other classes (`WinChecker`, `Display`, players) can loop without a hardcoded number.

---

## CellState (enum, own file)
What's on a single cell: `EMPTY`, `X`, or `O`. Each constant carries two fixed values, set once at class-load time:

```java
public enum CellState {
    EMPTY(0, ' '), X(1, 'X'), O(-1, 'O');

    private final int value;
    private final char symbol;

    CellState(int value, char symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue() { return value; }
    public char getSymbol() { return symbol; }
}
```

- `getValue()` — used by `WinChecker`'s win-sum math (`X` = 1, `O` = -1, so three-in-a-row sums to ±`BOARD_SIZE`).
- `getSymbol()` — used by `Display` when printing the board.

---

## Coordinate (record, own file)
```java
public record Coordinate(int row, int col) {}
```
A position on the board. Pure, immutable data — no logic, no methods of its own beyond what Java generates automatically.

---

## GameState (enum, own file)
The outcome of a game: `X_WON`, `O_WON`, `DRAW`, `NOT_FINISHED`. Each constant carries its own display message:

```java
public enum GameState {
    X_WON("Вы победили!"),
    O_WON("Компьютер победил!"),
    DRAW("Ничья!"),
    NOT_FINISHED("Игра не закончена");

    private final String message;

    GameState(String message) { this.message = message; }

    public String getMessage() { return message; }
}
```

---

## WinChecker
**Responsibility:** stateless — decides whether a board is won, drawn, or still in progress. No fields; every method takes the `Board` it needs to inspect as a parameter.

**Methods**
- `GameState checkGameState(Board board)` — public entry point. Gathers row, column, and diagonal sums, then delegates to `determineOutcome`.
- `private List<Integer> getRowSums(Board board)` — one sum per row.
- `private List<Integer> getColumnSums(Board board)` — one sum per column.
- `private List<Integer> getDiagonalSums(Board board)` — sum of each diagonal (2 values).
- `private GameState determineOutcome(List<Integer> sums, Board board)` — checks the sums against `board.getBoardSize()` (win), then `board.isBoardFull()` (draw), otherwise `NOT_FINISHED`.

---

## Player (interface)
The contract every player type implements:
- `Coordinate makeMove(Board board)` — decide where to move next.
- `CellState getSymbol()` — which symbol (`X` or `O`) this player places. Assigned once, at construction, by whoever builds the player.

### HumanPlayer implements Player
**Responsibility:** reads and validates console input.

**Fields:** `private final Scanner scanner`, `private final Display display`, `private final CellState symbol`

**Methods**
- `Coordinate makeMove(Board board)` — prints the prompt, reads a line, parses row/col, loops until `isValidMove` passes.
- `private boolean isValidMove(Coordinate coord, Board board)` — checks bounds and whether the cell is already occupied. Prints the relevant error via `Display` and returns `false` if either check fails.
- `CellState getSymbol()` — returns the symbol passed in at construction.

### BotPlayer implements Player
**Responsibility:** picks a random empty cell.

**Fields:** `private final Random random`, `private final Display display`, `private final CellState symbol`

**Methods**
- `Coordinate makeMove(Board board)` — prints the "thinking" message, delegates to `findRandomEmptyCell`.
- `private Coordinate findRandomEmptyCell(Board board)` — repeatedly picks a random row/col until it lands on one where `getCell(...) == EMPTY`.
- `CellState getSymbol()` — returns the symbol passed in at construction.

---

## Display
**Responsibility:** the only class allowed to touch `System.out`. Stateless — no fields, every method just takes what it needs to print.

**Methods**
- `void printBoard(Board board)` — prints the grid using each cell's `getSymbol()`.
- `void printWelcomeMessage()` — prints the title banner.
- `void printError(String message)` — prints a validation error.
- `void printCoordinatePrompt()` — prints the "enter row and column" prompt.
- `void printBotPrompt()` — prints the bot's "thinking" message.
- `void printGameState(GameState state)` — prints the final win/draw/not-finished message via `state.getMessage()`.

---

## Game
**Responsibility:** orchestrates one game. The only class that decides whose turn it is and applies a move to the board.

**Fields:** `private final Board board`, `private final Display display`, `private final WinChecker winChecker`, `private final Player playerX`, `private final Player playerO`, `private boolean isPlayerXTurn = true`

**Methods**
- `void startGameLoop()` — prints the welcome message and initial board, then loops: get the current player's move, write it to the board via `setCell` using that player's own `getSymbol()`, print the board, flip whose turn it is, ask `WinChecker` for the state, and stop (printing the result) once the state isn't `NOT_FINISHED`.

---

## Main
**Responsibility:** composition root — the only place in the whole program that constructs objects with `new`. Builds one `Scanner`, one `Display`, one `Board`, one `WinChecker`, and both `Player` implementations (each given its own `CellState` symbol at construction), wires them all into a `Game`, and starts it.

---

# Design principles applied
- **Encapsulation** — `Board`'s array is private; every read/write goes through `getCell`/`setCell`. No class ever gets a raw reference to the array.
- **Single responsibility** — each class does exactly one job: `Board` stores state, `WinChecker` evaluates it, `Display` prints it, `Player` decides moves, `Game` orchestrates turns.
- **Polymorphism over branching** — `Game` never checks "is this the human or the bot"; it just calls `player.makeMove(board)` and `player.getSymbol()` on whichever `Player` it's holding.
- **Dependency injection** — every class receives what it needs through its constructor (`Scanner`, `Display`, `Board`, etc.) rather than constructing its own dependencies. This is what makes `Display` a single shared instance across `HumanPlayer`, `BotPlayer`, and `Game`, and what would make each class independently unit-testable.
- **Composition root** — `Main` is the single place all the wiring happens; no other class ever calls `new` on another class in this list.
