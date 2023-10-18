package main.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import main.ControlModel.EventField;
import main.ControlModel.Field;
import main.ControlModel.ObserverField;

public class FieldButton extends JButton implements ObserverField {

    private final Color BG_DEFAULT = new Color(184, 184, 184);
    private final Color BG_FLAG = new Color(8, 179, 247);
    private final Color BG_TRIGGER = new Color(189, 66, 68);
    private final Color GREEN_TEXT = new Color(0, 100, 0);

    private Field field;

    public FieldButton(Field field) {
        this.field = field;
        setBackground(BG_DEFAULT);
        setBorder(BorderFactory.createBevelBorder(0));
    
    }

    @Override
    public void eventHasHappened(Field field, EventField event) {
        switch(event) {
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
    }

    private void aplyStileTrigger() {
    }

    private void aplyStileFlag() {
    }

    private void aplyStileOpen() {
    }

    
}
