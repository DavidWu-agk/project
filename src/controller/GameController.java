package controller;

import listener.GameListener;
import major.Main;
import model.*;
import view.*;

import javax.swing.*;
import java.io.*;

import static java.awt.AWTEventMulticaster.add;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener, Serializable {

    private boolean isMotion=true;

    private boolean avoidALotOfClick = false;

    public boolean isAvoidALotOfClick() {
        return avoidALotOfClick;
    }

    public void setAvoidALotOfClick(boolean avoidALotOfClick) {
        this.avoidALotOfClick = avoidALotOfClick;
    }

    private int step = 10;

    private int allStep = 10;
    private int aim = 50;
    private Chessboard model;
    private ChessboardComponent view;
    //private ChessGameFrame fr;
    private int nextstepCount = 0;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;
    private int num = 0;
    private String toDOString = "toDo:swap";
    private JLabel statusLabel;

    private JLabel theStepNumber;

    private JLabel aimNum;
    private JLabel toDO;

    public int getNextstepCount() {
        return nextstepCount;
    }

    //public ChessGameFrame getFr() {
    //return fr;
    //}

    //public void setFr(ChessGameFrame fr) {
    //this.fr = fr;
    //}

    public JLabel getToDO() {
        return toDO;
    }

    public void setToDO(JLabel toDO) {
        this.toDO = toDO;
    }

    public void setSelectedPoint(ChessboardPoint selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public void setSelectedPoint2(ChessboardPoint selectedPoint2) {
        this.selectedPoint2 = selectedPoint2;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public JLabel getTheStepNumber() {
        return theStepNumber;
    }

    public void setTheStepNumber(JLabel theStepNumber) {
        this.theStepNumber = theStepNumber;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public ChessboardPoint getSelectedPoint() {
        return selectedPoint;
    }

    public ChessboardPoint getSelectedPoint2() {
        return selectedPoint2;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
//        this.statusLabel.setText("Score:" + score);
//        this.theStepNumber.setText("the step you have:"+step);
    }

    public void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                //todo: complete it when restart game
            }
        }
    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        //交换的控制方法
        // TODO: Init your swap function here.
        if (model.isInMiddleState() == true) {
            showErrorDialog("Can't swap now.");
            setSelectedPoint(null);
            setSelectedPoint2(null);
            return;
        }
        if (selectedPoint == null | selectedPoint2 == null) {
            showErrorDialog("you haven't selected two point.");
            return;
        }
        if (model.isStuck() == true) {
            showErrorDialog("you stuck,please refreash");
        }

        if (step <= 0) {
            view.stepOut();
            view.winOrLose();
        } else if (selectedPoint != null && selectedPoint2 != null && model.CanSwap(selectedPoint, selectedPoint2) == true) {
            nextstepCount = 0;
            model.swapChessPiece(selectedPoint, selectedPoint2);//这是调用了model层的方法，model层是project的底层，有着各种判断棋子间关系与胜负等的方法
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint2, chess1);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint, chess2);
            chess1.repaint();
            chess2.repaint();
            //以下几行为消去代码
            model.scanTheChessBoard();
            score = score + model.basicCountPoint();
            step--;
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:" + step);
            this.aimNum.setText("aim:" + aim);
            chessComponentBasicElimilation();
            model.basicElimilation();
            model.setToDefault();
            selectedPoint = null;
            selectedPoint2 = null;//修复消了以后无法自由点击格子的bug
//            DownPlayer myClassInstance = new DownPlayer();
//            Thread thread2 = new Thread(myClassInstance);
//            System.out.printf("test");
//            thread2.start();
            toDOString = "toDo:nextStep";
            this.toDO.setText(toDOString);

            //step--;
            //this.theStepNumber.setText("the step you have:"+ step);
            //this.theStepNumber.repaint();
            //this.theStepNumber.setVisible(true);
            //view.setDeltaStep(-1);
        } else {
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint2, chess1);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint, chess2);
            chess1.repaint();
            chess2.repaint();
            view.cantSwap();
            view.setChessComponentAtGrid(selectedPoint2, chess2);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint, chess1);
            chess1.repaint();
            chess2.repaint();
            selectedPoint = null;
            selectedPoint2 = null;
            //view.setDeltaStep(0);
        }
        if (score >= aim) {
            view.stepOut();
            view.winOrLose();
        }
        System.out.println("Implement your swap here.");

    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        //TODO:如果不能再下落或消去，则提示需要swap
//        avoidALotOfClick=true;
        if (model.isInMiddleState() == false) {
            showErrorDialog("Can't implement next step now.");
//            avoidALotOfClick=false;
            return;
        }
        System.out.println("Implement your next step here.");
        if (nextstepCount % 3 == 0) {
//            model.chessDown();
            if(isMotion) {
                System.out.printf("\n\n\nchess down");
                chessDown();//down the chess
            }
            else {
                model.chessDown();
                sync();
            }
//            sync();
//            DownPlayer myClassInstance = new DownPlayer();
//            Thread thread2 = new Thread(myClassInstance);
//            System.out.printf("test");
//            thread2.start();
            nextstepCount++;
        } else if (nextstepCount % 3 == 1) {
            //todo: I try to use this to avoid the quick click on the NextStepButton
            if(isMotion) {
                if (!(canClick())) {

                } else {
//                model.generate();// generate the new chess in null grid
                    generate();
//                sync();
//                DownPlayer myClassInstance = new DownPlayer();
//                Thread thread2 = new Thread(myClassInstance);
//                System.out.printf("test");
//                thread2.start();
                    nextstepCount++;
                }
            }
            else {
                model.generate();// generate the new chess in null grid
                sync();
                nextstepCount++;
            }
        } else {
            if(isMotion){
                if (!(canClick1())) {

                } else {
                    model.scanTheChessBoard();
                    score = score + model.basicCountPoint();
                    chessComponentBasicElimilation();
                    model.basicElimilation();
                    model.setToDefault();//这些是第一次重复消去
                    this.statusLabel.setText("Score:" + score);//再次计分
                    selectedPoint = null;
                    selectedPoint2 = null;
//                DownPlayer myClassInstance = new DownPlayer();
//                Thread thread2 = new Thread(myClassInstance);
//                System.out.printf("test");
//                thread2.start();
                    nextstepCount++;
                }
            }
            else {
                model.scanTheChessBoard();
                score = score + model.basicCountPoint();
                chessComponentBasicElimilation();
                model.basicElimilation();
                model.setToDefault();//这些是第一次重复消去
                this.statusLabel.setText("Score:" + score);//再次计分
                selectedPoint = null;
                selectedPoint2 = null;
//                DownPlayer myClassInstance = new DownPlayer();
//                Thread thread2 = new Thread(myClassInstance);
//                System.out.printf("test");
//                thread2.start();
                nextstepCount++;
            }
        }
        if (score >= aim) {
            view.stepOut();
            view.winOrLose();
        }
        if (model.isInMiddleState() == false) {
            //TODO:自动存档，以便于悔棋，并删除前面一个存档
            File theAutoSave = new File("src/AutoSave.ser");
            try (FileOutputStream fileOut = new FileOutputStream(theAutoSave);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                objectOut.writeObject(this);
                System.out.println("Game has been saved to: " + theAutoSave.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("error");
            }
            toDOString = "toDo:swap";
            this.toDO.setText(toDOString);
        }
    }


    //TODO:重复消去生成的三连
        /*model.scanTheChessBoard();
        score=score+model.basicCountPoint();
        chessComponentBasicElimilation();
        model.basicElimilation();
        model.setToDefault();//这些是第一次重复消去
        this.statusLabel.setText("Score:" + score);//再次计分
        selectedPoint=null;
        selectedPoint2=null;
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                ChessboardPoint p = new ChessboardPoint(i,j);
                if(model.getGridAt(p).getPiece()==null){//若有空，则下落后继续检查重消
                    chessDownInGameController();//下落
                    model.scanTheChessBoard();
                    score=score+model.basicCountPoint();
                    chessComponentBasicElimilation();
                    model.basicElimilation();
                    model.setToDefault();//这些是后面的重复消去
                    this.statusLabel.setText("Score:" + score);//再次计分
                    i=0;
                    j=0;//下落重消后重新扫描棋盘
                }
            }
        }*///这段暂时可以不要，现在先点一次nextstep连环消去一次//


    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }


    }

    public void chessComponentBasicElimilation() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (model.getGrid()[i][j].isToRemoveRow() == true | model.getGrid()[i][j].isToRemoveCol() == true) {
                    //ChessComponent noChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece("-"));
                    ChessboardPoint p = new ChessboardPoint(i, j);
                    view.removeChessComponentAtGrid(p);
                    view.getGridComponentAt(p).repaint();
                    //view.setChessComponentAtGrid(p,noChess);
                    //noChess.repaint();

                }
            }
        }
    }

    public void onPlayerRefresh() {
        model.toRefresh();
        while (!(model.isGoodInit())) {
            model.toRefresh();
        }
        for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
            for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
                ChessboardPoint p = new ChessboardPoint(i, j);
                view.removeChessComponentAtGrid(p);
                ChessComponent thisChess;
                thisChess = new ChessComponent(view.getCHESS_SIZE(), new ChessPiece(model.getGridAt(p).getPiece().getName()));
                view.setChessComponentAtGrid(p, thisChess);
                thisChess.repaint();
            }
        }
    }

    public void onPlayerRestart() {
        score = 0;
        step = allStep;
        this.statusLabel.setText("Score:" + score);
        this.theStepNumber.setText("step:" + step);
        this.aimNum.setText("aim:" + aim);
        onPlayerRefresh();
    }


    public void chessDownInGameController() {
//        model.chessDown();//抽象棋盘里的下降
        chessDown();
        sync();
        sync();
    }

    public void sync() {//需要时同步model和view
        System.out.printf("sync begin\n");
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint p = new ChessboardPoint(i, j);
                if (model.getGrid()[i][j].getPiece() != null) {
                    ChessComponent thisChess = new ChessComponent(view.getCHESS_SIZE(), new ChessPiece(model.getGridAt(p).getPiece().getName()));
                    if (view.getGridComponentAt(p).getComponents().length != 0) {
                        view.removeChessComponentAtGrid(p);
                    }
                    view.setChessComponentAtGrid(p, thisChess);
                    thisChess.repaint();
//                    System.out.printf("repaint1\n");
                } else {
                    if (view.getGridComponentAt(p).getComponents().length != 0) {
                        view.removeChessComponentAtGrid(p);
                        view.getGridComponentAt(p).repaint();
                    }
//                    System.out.printf("repaint2\n");
                }

            }
        }
        System.out.printf("sync finish\n");
    }

    public void onChooseLevel() {
        String[] options = {"Easy", "Hard", "Customize"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:",
                "Options",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            //TODO:easy
            step = 10;
            allStep = step;
            aim = 50;
            score = 0;
            onPlayerRefresh();
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:" + step);
            this.aimNum.setText("aim:" + aim);
        } else if (choice == 1) {
            //TODO:hard
            step = 8;
            allStep = step;
            aim = 50;
            score = 0;
            onPlayerRefresh();
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:" + step);
            this.aimNum.setText("aim:" + aim);
        } else if (choice == 2) {
            //TODO:customize
            int step0 = step;
            int aim0 = aim;
            String str1;
            String str2;
            try {
                str1 = JOptionPane.showInputDialog("Enter step");
                if (str1 == null) {
                } else {
                    step = Integer.parseInt(str1);
                    if (step <= 0) {
                        view.numError();
                        step = step0;
                    } else {
                        str2 = JOptionPane.showInputDialog("Enter aim score");
                        if (str2 == null) {
                        } else {
                            aim = Integer.parseInt(str2);
                            if (aim <= 0) {
                                view.numError();
                                aim = aim0;
                            } else {
                                allStep = step;
                                score = 0;
                                onPlayerRefresh();
                                this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
                                this.theStepNumber.setText("step:" + step);
                                this.aimNum.setText("aim:" + aim);
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
                view.numError();
                step = step0;
                aim = aim0;
            }

        } else {
        }
    }


    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Chessboard getModel() {
        return model;
    }

    public void setLabel() {
        this.statusLabel.setText("Score:" + score);
        this.theStepNumber.setText("step:" + step);
        this.aimNum.setText("aim:" + aim);
        toDOString = "toDo:swap";
        this.toDO.setText(toDOString);
    }

    public int getAim() {
        return aim;
    }

    public void setAim(int aim) {
        this.aim = aim;
    }

    public JLabel getAimNum() {
        return aimNum;
    }

    public void setAimNum(JLabel aimNum) {
        this.aimNum = aimNum;
    }

    public static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.ERROR_MESSAGE);
    }

    public void onChangeMusic() {
        String[] options = {"Play", "Break"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:",
                "Options",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            String[] options1 = {"All", "Music1", "Music2", "Music3"};
            int choice1 = JOptionPane.showOptionDialog(null,
                    "Choose an option:",
                    "Options",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options1,
                    options1[0]);
            if (choice1 == 0) {
                if (Main.getOp().backMusic != null) {
                    Main.getOp().backMusic.pauseMusic();
                }
                if (Main.getOp().backMusic1 != null) {
                    Main.getOp().backMusic1.pauseMusic();
                }
                if (Main.getOp().backMusic2 != null) {
                    Main.getOp().backMusic2.pauseMusic();
                }
                if (Main.getOp().backMusic3 != null) {
                    Main.getOp().backMusic3.pauseMusic();
                }
                Thread thread1 = new Thread(Main.getOp().backMusic);
                thread1.start();
            } else if (choice1 == 1) {
                System.out.printf("test1");
                if (Main.getOp().backMusic != null) {
                    Main.getOp().backMusic.pauseMusic();
                }
                if (Main.getOp().backMusic1 != null) {
                    Main.getOp().backMusic1.pauseMusic();
                }
                if (Main.getOp().backMusic2 != null) {
                    Main.getOp().backMusic2.pauseMusic();
                }
                if (Main.getOp().backMusic3 != null) {
                    Main.getOp().backMusic3.pauseMusic();
                }
                System.out.printf("test2");
                Thread thread1 = new Thread(Main.getOp().backMusic1);
                thread1.start();
            } else if (choice1 == 2) {
                if (Main.getOp().backMusic != null) {
                    Main.getOp().backMusic.pauseMusic();
                }
                if (Main.getOp().backMusic1 != null) {
                    Main.getOp().backMusic1.pauseMusic();
                }
                if (Main.getOp().backMusic2 != null) {
                    Main.getOp().backMusic2.pauseMusic();
                }
                if (Main.getOp().backMusic3 != null) {
                    Main.getOp().backMusic3.pauseMusic();
                }
                Thread thread1 = new Thread(Main.getOp().backMusic2);
                thread1.start();
            } else if (choice1 == 3) {
                if (Main.getOp().backMusic != null) {
                    Main.getOp().backMusic.pauseMusic();
                }
                if (Main.getOp().backMusic1 != null) {
                    Main.getOp().backMusic1.pauseMusic();
                }
                if (Main.getOp().backMusic2 != null) {
                    Main.getOp().backMusic2.pauseMusic();
                }
                if (Main.getOp().backMusic3 != null) {
                    Main.getOp().backMusic3.pauseMusic();
                }
                Thread thread1 = new Thread(Main.getOp().backMusic3);
                thread1.start();
            } else {

            }
        } else if (choice == 1) {
            if (Main.getOp().backMusic != null) {
                Main.getOp().backMusic.pauseMusic();
            }
            if (Main.getOp().backMusic1 != null) {
                Main.getOp().backMusic1.pauseMusic();
            }
            if (Main.getOp().backMusic2 != null) {
                Main.getOp().backMusic2.pauseMusic();
            }
            if (Main.getOp().backMusic3 != null) {
                Main.getOp().backMusic3.pauseMusic();
            }
        } else {

        }
    }

    @Override
    public boolean equals(GameController gameController) {
        System.out.printf("testtesttest");
        for (int i = 0; i <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i++) {
            for (int j = 0; j <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; j++) {
                if (this.model.getGrid()[i][j].getPiece() != null && gameController.model.getGrid()[i][j].getPiece() != null) {
                    if (this.model.getGrid()[i][j].getPiece().getName().equals(gameController.model.getGrid()[i][j].getPiece().getName())) {
                        System.out.printf("getGrid()[%d][%d] equals\n", i, j);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
//        if (!(this.step==gameController.step && this.aimNum==gameController.aimNum && this.score==gameController.score)){
//            System.out.printf("also OK");
//            return false;
//        }
        return true;
    }

    public void setNextstepCount(int nextstepCount) {
        this.nextstepCount = nextstepCount;
    }

    public String getToDOString() {
        return toDOString;
    }

    public void setToDOString(String toDOString) {
        this.toDOString = toDOString;
    }

    public void setView(ChessboardComponent view) {
        this.view = view;
    }


    //todo
    public void chessDown() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
                    for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
                        if (model.getGrid()[i][j].getPiece() == null) {
                            System.out.printf("%d %d is null\n",i,j);
                            for (int k = i; k >= 0; k--) {

                                if (model.getGrid()[k][j].getPiece() != null) {
//                            grid[i][j].setPiece(grid[k][j].getPiece());
//                            grid[k][j].removePiece();
                                    //todo

                                    for (int m = k + 1; m <= i; m++) {
                                        model.getGrid()[m][j].setPiece(model.getGrid()[m - 1][j].getPiece());
                                        model.getGrid()[m - 1][j].removePiece();
                                        ChessboardPoint p = new ChessboardPoint(m, j);
//                                        System.out.printf("10086");
//                                        //todo
                                        sync();
                                        System.out.printf("%d %d down one\n",m-1,j);
                                        try {
                                            // 让当前线程暂停5秒钟
                                            Thread.sleep(35);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }




                            /*for(int m=0;m<Constant.CHESSBOARD_ROW_SIZE.getNum();m++) {
                                for (int l = 0; l < Constant.CHESSBOARD_COL_SIZE.getNum(); l++) {
                                    if (grid[m][l].getPiece()==null){
                                        System.out.printf("-\t");
                                    }
                                    else {
                                        System.out.printf("%s\t",grid[m][l].getPiece().getName());
                                    }
                                }
                                System.out.printf("\n");
                            }
                            System.out.printf("\n\n\n");*/
                                    break;
                                }
                            }
                        }
                    }
                }
                return null;
            }
        }.execute();
    }

    public boolean canClick() {
        for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
            for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
                if (model.getGrid()[i][j].getPiece() == null) {
                    for (int k = i; k >= 0; k--) {
                        if (model.getGrid()[k][j].getPiece() != null) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    public void generate() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
                    for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
                        //grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"😅", "😍", "😋", "😡"})));
                        if (model.getGrid()[i][j].getPiece() == null) {
                            model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"😅", "😍", "😋", "😡"})));
                            sync();
                            try {
                                // 让当前线程暂停5秒钟
                                Thread.sleep(35);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            }
        }.execute();
    }

    public boolean canClick1() {
        for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
            for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
                if (model.getGrid()[i][j].getPiece() == null) {
                    return false;
                }
            }
        }
        return true;
    }

//    public void initLoop(){
//        new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                for (int i = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; i >= 0; i--) {
//                    for (int j = Constant.CHESSBOARD_COL_SIZE.getNum() - 1; j >= 0; j--) {
//                        model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"😅", "😍", "😋", "😡"})));
//                        sync();
//                        try {
//                            // 让当前线程暂停5秒钟
//                            Thread.sleep(10);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                return null;
//            }
//        }.execute();
//    }


    public void onChangeMotion(){
        String[] options = {"On", "Off"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:",
                "Options",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (choice == 0) {
            isMotion=true;
        }
        else if (choice == 1){
            isMotion=false;
        }
        else {

        }
    }

}
