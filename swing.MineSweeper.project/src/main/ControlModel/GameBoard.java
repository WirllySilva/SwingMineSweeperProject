package main.ControlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class GameBoard {

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;

    private final List<Field> fields = new ArrayList<>();

    public GameBoard(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfMines = numberOfMines;

        generateField();
        connectAdjacentsSquares();
        raffleMines();
    }

    public void open(int row, int column) {
        try {
            fields.parallelStream()
                    .filter(f -> f.getRow() == row && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(f -> f.openField());
        } catch (Exception e) {
            //FIXME adapt the openField method
            fields.forEach(f -> f.setOpened(true));
            throw e;
        }
    }

    public void markingToggle(int row, int column) {
        fields.parallelStream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.markingToggle());
    }

    private void generateField() {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                fields.add(new Field(row, column));
            }
        }
    }

    private void connectAdjacentsSquares() {
        for (Field f1 : fields) {
            for (Field f2 : fields) {
                f1.addAdjacentSquare(f2);
            }
        }
    }

    private void raffleMines() {
        long armedMines = 0;
        Predicate<Field> mined = f -> f.isMinado();

        do {
            int randomValue = (int) (Math.random() * fields.size());
            fields.get(randomValue).layMine();
            armedMines = fields.stream().filter(mined).count();
        } while (armedMines < numberOfMines);
    }

    public boolean goalHasBeenMet() {
        return fields.stream().allMatch(f -> f.goalHasBeenMet());
    }

    public void restartGame() {
        fields.stream().forEach(f -> f.restartGame());
        raffleMines();
    }
}
