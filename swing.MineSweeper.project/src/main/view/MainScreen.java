package main.view;

import javax.swing.JFrame;

import main.ControlModel.GameBoard;

public class MainScreen extends JFrame {

    public MainScreen() {
        GameBoard gameBoard = new GameBoard(16, 30, 50);
        add(new GameBoardPanel(gameBoard));

        setTitle("Mine_Swepper");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        
        new MainScreen();
    }
    
}
