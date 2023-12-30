package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import controller.Load;
import controller.Save;
import major.Main;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;
    //ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;

    private JLabel aimNum;

    private JLabel theStepNumber;
    private JLabel toDO;
    private int step;

    private int theChance=3;
    private int autoMode=0;
    //public ScheduledExecutorService getExecutor() {
        //return executor;
    //}

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
        addLevelButton();
        addAutoModeButton();
        addHintButton();
        addBackButton();
        addMusicButton();
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
        this.aimNum = new JLabel("aim:");
        aimNum.setLocation(HEIGTH, HEIGTH / 10+25);
        aimNum.setSize(200, 60);
        aimNum.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(aimNum);
        this.theStepNumber = new JLabel("step:");
        theStepNumber.setLocation(HEIGTH, HEIGTH / 10+50);
        theStepNumber.setSize(200, 60);
        theStepNumber.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(theStepNumber);
        this.toDO =new JLabel("toDo:swap");
        toDO.setLocation(HEIGTH, HEIGTH / 10+75);
        toDO.setSize(200, 60);
        toDO.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(toDO);
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
            if (gameController.getModel().isInMiddleState()==true){
                GameController.showErrorDialog("Can't refresh now.");
                return;
            }
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
            File theAutoSave= new File("src/AutoSave.ser");
            try (FileOutputStream fileOut = new FileOutputStream(theAutoSave);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                objectOut.writeObject(gameController);
                System.out.println("Game has been saved to: " + theAutoSave.getAbsolutePath());

            } catch (IOException f) {
                f.printStackTrace();
                System.out.printf("error");
            }
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



    private JButton NextStepButton;
    private void addNextStepButton() {

        NextStepButton = new JButton("Next Step");
        NextStepButton.addActionListener((e) -> {
            chessboardComponent.nextStep();
        });
        NextStepButton.setLocation(HEIGTH, HEIGTH / 10 + 280);
        NextStepButton.setSize(200, 60);
        NextStepButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(NextStepButton);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            GameController gc=Load.loadController();
            if(gc!=null){
            gameController.getModel().setGrid(gc.getModel().getGrid());
            gameController.sync();
            gameController.setStep(gc.getStep());
            gameController.setScore(gc.getScore());
            gameController.setAim(gc.getAim());
            gameController.setToDOString(gc.getToDOString());
            gameController.getStatusLabel().setText("Score:"+gameController.getScore()/*+"\nthe step you have:"+step*/);
            gameController.getTheStepNumber().setText("step:"+gameController.getStep());
            gameController.getAimNum().setText("aim:"+gameController.getAim());
            gameController.getToDO().setText(gameController.getToDOString());//???
            }
        });
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            if (gameController.getModel().isInMiddleState()==true){
                GameController.showErrorDialog("Can't save now");
                return;
            }
            Save.saveGame(gameController);
        });
    }

    public void addLevelButton(){
        JButton button=new JButton("Level");
        button.setLocation(HEIGTH, HEIGTH / 10 + 600);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            chessboardComponent.chooseLevel();
        });
    }
    public void addAutoModeButton(){
        JButton button=new JButton("Auto mode");
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            if (gameController.getModel().isInMiddleState()==false){
            if (autoMode%2==0){
            if(gameController.getModel().isStuck()==false&isGameFinish()==false){
                autoMode++;
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                executor.schedule(() -> {//这大概是起到了一个保护的作用
                    //while (isGameFinish()==false) {
                        automode();
                        //sleep();
                        /*if (isGameFinish()==true){
                            if (gameController.getModel().isInMiddleState()==false){
                                gameController.setSelectedPoint(gameController.getModel().hint().get(0));
                                gameController.setSelectedPoint2(gameController.getModel().hint().get(1));
                                var point1 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint()).getComponent(0);
                                var point2 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint2()).getComponent(0);
                                point1.setSelected(true);
                                point2.setSelected(true);
                                point1.repaint();
                                point2.repaint();
                                gameController.onPlayerSwapChess();

                            }
                    }*/
                //}
                    autoMode++;
                executor.shutdown();// 停止执行后关闭 executor

                }, 0, TimeUnit.MILLISECONDS);
            }}else {
                GameController.showErrorDialog("can't use now");
            }
            }else {
                GameController.showErrorDialog("can't use it now");
            }








        });
    }
    public void addHintButton(){
        JButton button=new JButton("Hint");
        button.setLocation(HEIGTH+200, HEIGTH / 10 +200 );
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            gameController.setSelectedPoint(gameController.getModel().hint().get(0));
            gameController.setSelectedPoint2(gameController.getModel().hint().get(1));
            var point1 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint()).getComponent(0);
            var point2 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint2()).getComponent(0);
            point1.setSelected(true);
            point2.setSelected(true);
            point1.repaint();
            point2.repaint();
        });
    }

    private void addBackButton() {
        JButton button = new JButton("Back");
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            try (FileInputStream fileIn = new FileInputStream("src/AutoSave.ser");
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                GameController gc = (GameController) objectIn.readObject();
                if(!(gc.equals(gameController)) && gameController.getNextstepCount()==0) {
                    System.out.printf("114514");
                    System.out.println("Game has been back");
                    //
                    gameController.getModel().setGrid(gc.getModel().getGrid());
                    gameController.sync();
                    gameController.setStep(gc.getStep());
                    gameController.setScore(gc.getScore());
                    gameController.setAim(gc.getAim());
                    gameController.setToDOString(gc.getToDOString());
                    gameController.getStatusLabel().setText("Score:" + gameController.getScore()/*+"\nthe step you have:"+step*/);
                    gameController.getTheStepNumber().setText("step:" + gameController.getStep());
                    gameController.getAimNum().setText("aim:" + gameController.getAim());
                    gameController.getToDO().setText(gameController.getToDOString());//???
                }
                else {
                    GameController.showErrorDialog("Only can back after swap.");
                }

            } catch (IOException | ClassNotFoundException f) {
                GameController.showErrorDialog("Only can back after swap.");
            }
        });
    }

    private void addMusicButton() {
        JButton button = new JButton("Music");
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            chessboardComponent.changeMusic();
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

    public JLabel getAimNum() {
        return aimNum;
    }

    public JLabel getToDO() {
        return toDO;
    }

    public void setAimNum(JLabel aimNum) {
        this.aimNum = aimNum;
    }
    public void automode(){
        if (gameController.getModel().isInMiddleState()==false&isGameFinish()==false){
            gameController.setSelectedPoint(gameController.getModel().hint().get(0));
            gameController.setSelectedPoint2(gameController.getModel().hint().get(1));
            var point1 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint()).getComponent(0);
            var point2 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint2()).getComponent(0);
            point1.setSelected(true);
            point2.setSelected(true);
            point1.repaint();
            point2.repaint();
            sleep();
            gameController.onPlayerSwapChess();
            sleep();
            while (gameController.getModel().isInMiddleState()==true) {
                    gameController.onPlayerNextStep();
                    sleep();
            }



        }

    }


    public void sleep(){
        System.out.println("Start");

        try {
            // 让当前线程暂停5秒钟
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End after 0.5 seconds");
    }
    public boolean isGameFinish(){
        if ((gameController.getStep()<=0)|(gameController.getScore()>=gameController.getAim())){
            return true;
        }
        return false;
    }
}
