package main.view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.ControlModel.GameBoard;

public class GameBoardPanel extends JPanel {


    public GameBoardPanel(GameBoard gameBoard) {
        setLayout(new GridLayout(
            gameBoard.getNumberOfRows(), gameBoard.getNumberOfColumns()));

       gameBoard.forEachField(f -> add(new FieldButton(f)));
       gameBoard.recordObservers(e -> {
        //TODO show the resulto to the player.
       });

       
    }

    
}
