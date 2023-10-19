package main.ControlModel;

import java.util.ArrayList;
import java.util.List;

public class Field {

    private final int row;
    private final int column;

    private boolean opened = false;
    private boolean mined = false;
    private boolean flagged = false;

    private List<Field> adjacentSquares = new ArrayList<>();
    private List<ObserverField> observers = new ArrayList<>();

    Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void recordObservers(ObserverField observer) {
        observers.add(observer);
    }

    public void notifyObeservers(EventField event) {
        observers.stream().forEach(o -> o.eventHasHappened(this, event));
    }

    boolean addAdjacentSquare(Field adjacentSquare) {
        boolean differentRow = row != adjacentSquare.row;
        boolean differentColumn = column != adjacentSquare.column;
        boolean diagonallyAdjacent = differentRow && differentColumn;

        int deltaRow = Math.abs(row - adjacentSquare.row);
        int deltaColumn = Math.abs(column - adjacentSquare.column);
        int generalDelta = deltaColumn + deltaRow;

        if (generalDelta == 1 && !diagonallyAdjacent) {
            adjacentSquares.add(adjacentSquare);
            return true;
        } else if (generalDelta == 2 && diagonallyAdjacent) {
            adjacentSquares.add(adjacentSquare);
            return true;
        } else {
            return false;
        }
    }

    public void flaggingToggle() {
        if (!opened) {
            flagged = !flagged;

            if(flagged) {
                notifyObeservers(EventField.FLAG);
            } else {
                notifyObeservers(EventField.UNFLAG);
            }
        }
    }

    public boolean openField() {

        if (!opened && !flagged) {
            opened = true;

            if (mined) {
                notifyObeservers(EventField.TRIGGER);
                return true;
            }

            setOpened(true);
            
            if (safeAdjacentSquare()) {
                adjacentSquares.forEach(adjSquare -> adjSquare.openField());
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean safeAdjacentSquare() {
        return adjacentSquares.stream().noneMatch(adjSquare -> adjSquare.mined);
    }

    void layMine() {
        mined = true;
    }

    public boolean isMinado() {
        return mined;
    }

    void removeMine() {
        mined = false;
    }

    public boolean isflagged() {
        return flagged;
    }

    public boolean isMined() {
        return mined;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;

        if(opened) {
            notifyObeservers(EventField.OPEN);
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    boolean goalHasBeenMet() {
        boolean revealed = !mined && opened;
        boolean safeguarded = flagged;
        return revealed || safeguarded;
    }

    public int minesOnAdjacentSquare() {
        return (int) adjacentSquares.stream().filter(adjSquare -> adjSquare.mined).count();
    }

    void restartGame() {
        opened = false;
        mined = false;
        flagged = false;
        notifyObeservers(EventField.RESTART);
    }

    
}