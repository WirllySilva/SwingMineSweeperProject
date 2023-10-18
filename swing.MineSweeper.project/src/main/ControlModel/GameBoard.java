package main.ControlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GameBoard implements ObserverField{

    private int numberOfRows;
    private int numberOfColumns;
    private int numberOfMines;

    private final List<Field> fields = new ArrayList<>();
    private final List<Consumer<EventResult>> observers = new ArrayList<>();

    public GameBoard(int numberOfRows, int numberOfColumns, int numberOfMines) {
        this.numberOfRows = numberOfRows;
        this.numberOfColumns = numberOfColumns;
        this.numberOfMines = numberOfMines;

        generateField();
        connectAdjacentsSquares();
        raffleMines();
    }

    public void recordObservers(Consumer<EventResult> observer) {
        observers.add(observer);
    }

    private void notifyObeservers(boolean result) {
        observers.stream().forEach(o -> o.accept(new EventResult(result)));
    }

    public void open(int row, int column) {
            fields.parallelStream()
                    .filter(f -> f.getRow() == row && f.getColumn() == column)
                    .findFirst()
                    .ifPresent(f -> f.openField());    
    }

    public void markingToggle(int row, int column) {
        fields.parallelStream()
                .filter(f -> f.getRow() == row && f.getColumn() == column)
                .findFirst()
                .ifPresent(f -> f.flaggingToggle());
    }

    private void generateField() {
        for (int row = 0; row < numberOfRows; row++) {
            for (int column = 0; column < numberOfColumns; column++) {
                Field field = new Field(row, column);
                field.recordObservers(this);
                fields.add(field);
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

    @Override
    public void eventHasHappened(Field field, EventField event) {
        if(event == EventField.TRIGGER) {
            showMines();
            notifyObeservers(false);
        }else if(goalHasBeenMet()) {
            notifyObeservers(true);
        }
    }

    private void showMines() {
        fields.stream()
            .filter(f -> f.isMinado())
            .forEach(f -> f.setOpened(true));
    }
}
