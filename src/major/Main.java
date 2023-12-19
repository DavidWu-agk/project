package major;

import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.OpeningPage;

import javax.swing.*;

public class Main {
    private static OpeningPage op;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            op=new OpeningPage(1000,563);
            op.setVisible(true);
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
