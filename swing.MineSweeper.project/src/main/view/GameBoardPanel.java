package main.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.ControlModel.GameBoard;

public class GameBoardPanel extends JPanel {

    public GameBoardPanel(GameBoard gameBoard) {
        setLayout(new GridLayout(
                gameBoard.getNumberOfRows(), gameBoard.getNumberOfColumns()));

        gameBoard.forEachField(f -> add(new FieldButton(f)));
        gameBoard.recordObservers(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e.isYouWin()) {
                    JOptionPane.showMessageDialog(this, "You Win :)");
                } else {
                    JOptionPane.showMessageDialog(this, "You Lose :(");
                }
                gameBoard.restartGame();
            });
        });

    }

}
