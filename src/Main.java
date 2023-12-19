import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.OpeningPage;

import javax.swing.*;

public class
Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OpeningPage op=new OpeningPage(1000,563);
            op.setVisible(true);
            //ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            //GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());mainFrame.setGameController(gameController);
            //gameController.setStatusLabel(mainFrame.getStatusLabel());
            //mainFrame.setVisible(true);

        });
    }

}
