package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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
        addTipButton();
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
        addMotionButton();
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


//    public int getTheChance() {
//        return theChance;
//    }
//
//    public void setTheChance(int theChance) {
//        this.theChance = theChance;
//    }

    public JButton refreshButton;
    private void addRefreshButton() {

        refreshButton = new JButton("Refresh");
        refreshButton.setText("Refresh");
        refreshButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        refreshButton.addActionListener(e -> {
            //JOptionPane.showMessageDialog(this, "Show hello world");
            if (gameController.getModel().isInMiddleState()==true){
                GameController.showErrorDialog("Can't refresh now.");
                return;
            }
            if(gameController.getTheChance()>0){
                chessboardComponent.refresh();
                gameController.setTheChance(gameController.getTheChance()-1);
                refreshButton.setText("Refresh");
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
        refreshButton.setLocation(HEIGTH, HEIGTH / 10 + 120);
        refreshButton.setSize(200, 60);
        add(refreshButton);
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
            gameController.setTheChance(3);
            refreshButton.setText("Refresh");
            //TODO:有些bug
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
                if(gameController.isBig()==gc.isBig()){
                    Main.isBig=gc.isBig();
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
                    gameController.setTheChance(3);
                    refreshButton.setText("Refresh");
                    if (gameController.getModel().isStuck() == true) {
                        GameController.showErrorDialog("you stuck,please refreash");
                    }
                }
                else {
                    GameController.showErrorDialog("102,The chessboard size is wrong.");
                    return;
                }
//                Main.isBig=gc.isBig();
//                gameController.getModel().setGrid(gc.getModel().getGrid());
//                gameController.sync();
//                gameController.setStep(gc.getStep());
//                gameController.setScore(gc.getScore());
//                gameController.setAim(gc.getAim());
//                gameController.setToDOString(gc.getToDOString());
//                gameController.getStatusLabel().setText("Score:"+gameController.getScore()/*+"\nthe step you have:"+step*/);
//                gameController.getTheStepNumber().setText("step:"+gameController.getStep());
//                gameController.getAimNum().setText("aim:"+gameController.getAim());
//                gameController.getToDO().setText(gameController.getToDOString());//???
//
//                if (gameController.getModel().isStuck() == true) {
//                    GameController.showErrorDialog("you stuck,please refreash");
//                }
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
            if(gameController.getSelectedPoint()==null&gameController.getSelectedPoint2()==null){
                if (gameController.getModel().isStuck() == true) {
                    GameController.showErrorDialog("you stuck,please refreash");
                }else {
                    gameController.setSelectedPoint(gameController.getModel().hint().get(0));
                    gameController.setSelectedPoint2(gameController.getModel().hint().get(1));
                    var point1 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint()).getComponent(0);
                    var point2 = (ChessComponent) gameController.getView().getGridComponentAt(gameController.getSelectedPoint2()).getComponent(0);
                    point1.setSelected(true);
                    point2.setSelected(true);
                    point1.repaint();
                    point2.repaint();
                }
            }else{
                GameController.showErrorDialog("please cancel your current selection");
            }
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
                    gameController.getToDO().setText("toDo:swap");//???
                    if (gameController.getModel().isInMiddleState() == false) {
                        //TODO:自动存档，以便于悔棋，并删除前面一个存档
                        File theAutoSave = new File("src/AutoSave.ser");
                        try (FileOutputStream fileOut = new FileOutputStream(theAutoSave);
                             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                            objectOut.writeObject(gameController);
                            System.out.println("Game has been saved to: " + theAutoSave.getAbsolutePath());

                        } catch (IOException f) {
                            f.printStackTrace();
                            System.out.printf("error");
                        }
                        //toDOString = "toDo:swap";
                        //this.toDO.setText(toDOString);
                    }
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

    private void addMotionButton() {
        JButton button = new JButton("Motion");
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            chessboardComponent.changeMotion();
        });
    }

    private void addTipButton(){
        JButton button = new JButton("Tips");
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 520);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            String[] manualSections = {
                    "匹配规则：玩家通过交换相邻的（不包括对角线相邻）方块的\n位置来进行移动。当三个或更多相同图案的方块在一条直线上\n（水平或垂直）对齐时，它们被清除，并得分。",
                    "Auto mode可以自动完成一个周期",
                    "Hint可以让玩家获得下一步的提示",
                    "Back可以允许玩家悔棋",
                    "Music可切换和暂停音乐",
                    "Motion可打开或关闭动画模式",
                    "Refresh可刷新棋盘，每局有三次机会，Restart可重新开始这关游戏",
                    "Level选择难度并重新开始一轮游戏",
                    "Save和Load保存和加载游戏"
            };
            AtomicInteger currentIndex = new AtomicInteger(0);

            // 创建文本区域并设置初始内容
            JTextArea textArea = new JTextArea(10, 30);
            textArea.setText(manualSections[currentIndex.get()]);
            textArea.setEditable(false); // 禁止编辑

            // 将文本区域放到滚动窗格中
            JScrollPane scrollPane = new JScrollPane(textArea);

            // 创建"下一条"按钮
            JButton nextButton = new JButton("下一条");
            nextButton.addActionListener(f -> {
                // 切换到下一条内容
                int nextIndex = currentIndex.incrementAndGet();
                if (nextIndex < manualSections.length) {
                    textArea.setText(manualSections[nextIndex]);
                } else {
                    // 显示完所有内容后关闭弹窗
                    JOptionPane.getRootFrame().dispose();
                }
            });

            // 创建面板，将文本区域和按钮放置在一起
            JPanel panel = new JPanel();
            panel.add(scrollPane);
            panel.add(nextButton);

            // 显示弹窗
            JOptionPane.showMessageDialog(null, panel, "游戏说明书", JOptionPane.INFORMATION_MESSAGE);


        });
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
