package major;

import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.OpeningPage;

import javax.swing.*;
//nmsl
public class Main {
    public static boolean isBig=true;

    private static OpeningPage op;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            op=new OpeningPage(1000,563);
            op.setVisible(true);
            String[] options = {"Big", "Small"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Chessboard size:",
                    "Options",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (choice == 0) {
                isBig=true;
            }
            if (choice==1){
                isBig=false;
            }else {

            }
            //ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            //GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());mainFrame.setGameController(gameController);
            //gameController.setStatusLabel(mainFrame.getStatusLabel());

            //mainFrame.setVisible(true);
        });
    }

    public static OpeningPage getOp() {
        return op;
    }
}
