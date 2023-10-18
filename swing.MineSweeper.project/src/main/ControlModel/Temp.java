package main.ControlModel;

public class Temp {

    public static void main(String[] args) {
        
        GameBoard gameBoard1 = new GameBoard(3, 3, 0);

        gameBoard1.recordObservers(e -> {
            if(e.isYouWin()) {
                System.out.println("You Win... :)");
            } else {
                System.out.println("You Lose... :(");
            }
        });
        gameBoard1.open(0, 0);
        gameBoard1.open(0, 1);
        gameBoard1.open(0, 2);
        gameBoard1.open(1, 0);
        gameBoard1.open(1, 1);
        gameBoard1.open(1, 2);
        gameBoard1.open(2, 0);
        gameBoard1.open(2, 1);
        gameBoard1.open(2, 2);        
       
    }
    
}
