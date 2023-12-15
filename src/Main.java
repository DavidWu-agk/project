import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);

            //瞎jb加个背景尝试一下
            /*JPanel panel = new JPanel(){
                protected void paintComponent(Graphics g){
                    super.paintComponent(g);
                    Image image = new ImageIcon("back.jpg").getImage();
                    g.drawImage(image,0,0,getWidth(),getHeight(),this);
                }

            };
            mainFrame.add(panel);*/
            //
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setStatusLabel(mainFrame.getStatusLabel());
            mainFrame.setVisible(true);
        });
    }
}
