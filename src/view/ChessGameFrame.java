package view;

import controller.*;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

import major.Main;
import model.Chessboard;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements Serializable {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;

    private JLabel theStepNumber;

    private int step;

    private int theChance=3;



    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        ImageIcon backgroundImage = new ImageIcon(Main.getOp().getPath()); // 替换为你的图片路径
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, width,height);
        addChessboard();
        addLabel();
        //addTheStepLabel();
        //addHelloButton();
        addRefreshButton();
        addRestartButton();
        addSwapConfirmButton();
        addNextStepButton();
        addLoadButton();
        addSaveButton();
        add(backgroundLabel);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        this.statusLabel = new JLabel("Score:");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        this.theStepNumber = new JLabel("the step you have:");
        theStepNumber.setLocation(HEIGTH, HEIGTH / 10+50);
        theStepNumber.setSize(200, 60);
        theStepNumber.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(theStepNumber);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }



    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    //TODO:写一个label，用以表示剩下的步数
    /*public void addTheStepLabel(){
        setStep(10);
        this.theStepNumber = new JLabel("the step you have:"+ getStep());
        theStepNumber.setLocation(HEIGTH, HEIGTH / 10+30);
        theStepNumber.setSize(200, 60);
        theStepNumber.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(theStepNumber);
    }*/


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    /*private void addHelloButton() {
        JButton button = new JButton("Hello");
        button.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Show hello world");
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }*/


    public int getTheChance() {
        return theChance;
    }

    public void setTheChance(int theChance) {
        this.theChance = theChance;
    }

    private void addRefreshButton() {

        JButton button = new JButton("Refresh:"+theChance);
        button.setText("Refresh:"+theChance);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener(e -> {
            //JOptionPane.showMessageDialog(this, "Show hello world");
            if(theChance>0){
                chessboardComponent.refresh();
                theChance--;
                button.setText("Refresh:"+theChance);
            }
            else {
                JDialog dialog = new JDialog();
                dialog.setSize(300, 200);
                dialog.setLocationRelativeTo(null); // 居中显示
                JLabel label = new JLabel("the number of chance is 0.");
                Font font = new Font("Serif",Font.BOLD,24);
                label.setFont(font);
                dialog.add(label,BorderLayout.CENTER);
                dialog.setVisible(true);
            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        add(button);
    }
    private void addRestartButton() {
        JButton button = new JButton("restart");
        button.setLocation(HEIGTH, HEIGTH / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            chessboardComponent.restart();
            theChance=3;
            addRefreshButton();//TODO:有些bug
        });
    }
    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> {
            chessboardComponent.swapChess();
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            //System.out.println("Click load");
            //String path = JOptionPane.showInputDialog(this, "Input Path here");
            //System.out.println(path);
//            gameController.loadGameFromFile(path);
            GameController gameController = Load.loadGameController();
            ChessGameFrame frame=gameController.getFrame();
            gameController.setStatusLabel(frame.getStatusLabel());
            gameController.setTheStepNumber(frame.getTheStepNumber());
            gameController.setLabel();
            frame.setVisible(true);
            this.setVisible(false);
        });
    }
    public void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
                Main.getOp().getGameController().save();
        });
    }


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public JLabel getTheStepNumber() {
        return theStepNumber;
    }

    public void setTheStepNumber(JLabel theStepNumber) {
        this.theStepNumber = theStepNumber;
    }
}
