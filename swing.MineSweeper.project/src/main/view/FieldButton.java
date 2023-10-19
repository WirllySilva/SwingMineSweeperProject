package main.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import main.ControlModel.EventField;
import main.ControlModel.Field;
import main.ControlModel.ObserverField;

public class FieldButton extends JButton implements ObserverField, MouseListener {

    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_FLAG = new Color(8, 179, 247);
    private final Color BG_TRIGGER = new Color(189, 66, 68);
    private final Color GREEN_TEXT = new Color(0, 100, 0);

    private Field field;

    public FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        addMouseListener(this);
        field.recordObservers(this);
    }

    @Override
    public void eventHasHappened(Field field, EventField event) {
        switch (event) {
            case OPEN:
                aplyStileOpen();
                break;
            case FLAG:
                aplyStileFlag();
                break;
            case TRIGGER:
                aplyStileTrigger();
                break;
            default:
                aplyStileDefault();
        }

    }

    private void aplyStileDefault() {
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplyStileTrigger() {
        setBackground(BG_TRIGGER);
        setForeground(Color.BLACK);
        setText("X");
    }

    private void aplyStileFlag() {
        setBackground(BG_FLAG);
        setText("F");
    }

    private void aplyStileOpen() {

        setBorder(BorderFactory.createLineBorder(Color.gray));

        if (field.isMined()) {
            setBackground(BG_TRIGGER);
            return;

        }
        setBackground(BG_DEFAULT);

        switch (field.minesOnAdjacentSquare()) {
            case 1:
                setForeground(GREEN_TEXT);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String numberOfMinesOnAdjSquare = !field.safeAdjacentSquare() ? field.minesOnAdjacentSquare() + "" : "";
        setText(numberOfMinesOnAdjSquare);
    }

    // Mouse interface events.
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            field.openField();
        } else {
            field.flaggingToggle();
        }
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

}
