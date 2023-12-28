package controller;

import listener.GameListener;
import major.Main;
import model.ChessPiece;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import view.*;

import javax.swing.*;
import java.awt.*;
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


    private int step=10;

    private int allStep=10;
    private int aim=50;
    private Chessboard model;
    private ChessboardComponent view;
    private ChessGameFrame fr;
    private int nextstepCount=0;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;

    private JLabel statusLabel;

    private JLabel theStepNumber;

    private JLabel aimNum;
    private JLabel toDO;

    public int getNextstepCount() {
        return nextstepCount;
    }

    public ChessGameFrame getFr() {
        return fr;
    }

    public void setFr(ChessGameFrame fr) {
        this.fr = fr;
    }

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
        if (model.isInMiddleState()==true){
            showErrorDialog("Can't swap now.");
            return;
        }
        if (selectedPoint==null|selectedPoint2==null){
            showErrorDialog("you haven't selected two point.");
            return;
        }
        if (model.isStuck()==true){
            showErrorDialog("you stuck,please refreash");
        }

        if(step<=0){
            view.stepOut();
            view.winOrLose();
        }
        else if(selectedPoint!=null && selectedPoint2!=null&&model.CanSwap(selectedPoint,selectedPoint2)==true){
            nextstepCount=0;
            model.swapChessPiece(selectedPoint,selectedPoint2);//这是调用了model层的方法，model层是project的底层，有着各种判断棋子间关系与胜负等的方法
            ChessComponent chess1= view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2= view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint2,chess1);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint,chess2);
            chess1.repaint();
            chess2.repaint();
            //以下几行为消去代码
            model.scanTheChessBoard();
            score=score+model.basicCountPoint();
            step--;
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:"+step);
            this.aimNum.setText("aim:"+aim);
            chessComponentBasicElimilation();
            model.basicElimilation();
            model.setToDefault();
            selectedPoint=null;
            selectedPoint2=null;//修复消了以后无法自由点击格子的bug
            DownPlayer myClassInstance = new DownPlayer();
            Thread thread2 = new Thread(myClassInstance);
            System.out.printf("test");
            thread2.start();
            this.toDO.setText("toDo:nextStep");

            //step--;
            //this.theStepNumber.setText("the step you have:"+ step);
            //this.theStepNumber.repaint();
            //this.theStepNumber.setVisible(true);
            //view.setDeltaStep(-1);
        }else {
            ChessComponent chess1= view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2= view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint2,chess1);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint,chess2);
            chess1.repaint();
            chess2.repaint();
            view.cantSwap();
            view.setChessComponentAtGrid(selectedPoint2,chess2);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint,chess1);
            chess1.repaint();
            chess2.repaint();
            selectedPoint=null;
            selectedPoint2=null;
            //view.setDeltaStep(0);
        }
        if(score>=aim){
            view.stepOut();
            view.winOrLose();
        }
        System.out.println("Implement your swap here.");

    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        //TODO:如果不能再下落或消去，则提示需要swap
        if(model.isInMiddleState()==false){
            showErrorDialog("Can't implement next step now.");
            return;
        }
        System.out.println("Implement your next step here.");
        if(nextstepCount%3==0){
            model.chessDown();
            sync();
            DownPlayer myClassInstance = new DownPlayer();
            Thread thread2 = new Thread(myClassInstance);
            System.out.printf("test");
            thread2.start();
            nextstepCount++;
        } else if (nextstepCount%3==1) {
            model.generate();
            sync();
            DownPlayer myClassInstance = new DownPlayer();
            Thread thread2 = new Thread(myClassInstance);
            System.out.printf("test");
            thread2.start();
            nextstepCount++;
        }else{
        model.scanTheChessBoard();
        score=score+model.basicCountPoint();
        chessComponentBasicElimilation();
        model.basicElimilation();
        model.setToDefault();//这些是第一次重复消去
        this.statusLabel.setText("Score:" + score);//再次计分
        selectedPoint=null;
        selectedPoint2=null;
            DownPlayer myClassInstance = new DownPlayer();
            Thread thread2 = new Thread(myClassInstance);
            System.out.printf("test");
            thread2.start();
        nextstepCount++;
        }
        if(score>=aim){
            view.stepOut();
            view.winOrLose();
        }
        if(model.isInMiddleState()==false){
            //TODO:自动存档，以便于悔棋，并删除前面一个存档
            File theAutoSave= new File("src/AutoSave.ser");
            try (FileOutputStream fileOut = new FileOutputStream(theAutoSave);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                objectOut.writeObject(this);
                System.out.println("Game has been saved to: " + theAutoSave.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("error");
            }
            this.toDO.setText("toDo:swap");
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
    public void chessComponentBasicElimilation(){
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(model.getGrid()[i][j].isToRemoveRow()==true|model.getGrid()[i][j].isToRemoveCol()==true){
                    //ChessComponent noChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece("-"));
                    ChessboardPoint p=new ChessboardPoint(i,j);
                    view.removeChessComponentAtGrid(p);
                    view.getGridComponentAt(p).repaint();
                    //view.setChessComponentAtGrid(p,noChess);
                    //noChess.repaint();

                }
            }
        }
    }

    public void onPlayerRefresh(){
        model.toRefresh();
        while (!(model.isGoodInit())){
            model.toRefresh();
        }
        for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--) {
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

    public void onPlayerRestart(){
        score=0;
        step=allStep;
        this.statusLabel.setText("Score:" + score);
        this.theStepNumber.setText("step:"+step);
        this.aimNum.setText("aim:"+aim);
        onPlayerRefresh();
    }


    public void chessDownInGameController(){
        model.chessDown();//抽象棋盘里的下降
        sync();
        sync();
    }
    public void sync(){//需要时同步model和view
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                ChessboardPoint p =new ChessboardPoint(i,j);
                if(model.getGrid()[i][j].getPiece()!=null){
                    ChessComponent thisChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece(model.getGridAt(p).getPiece().getName()));
                    if(view.getGridComponentAt(p).getComponents().length!=0){
                        view.removeChessComponentAtGrid(p);
                    }
                    view.setChessComponentAtGrid(p,thisChess);
                    thisChess.repaint();
                }else {
                    if(view.getGridComponentAt(p).getComponents().length!=0){
                        view.removeChessComponentAtGrid(p);
                        view.getGridComponentAt(p).repaint();
                    }
                }

            }
        }
    }
    public void onChooseLevel(){
        String[] options = {"Easy", "Hard", "Customize"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:",
                "Options",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(choice==0){
            //TODO:easy
            step=10;
            allStep=step;
            aim=50;
            score=0;
            onPlayerRefresh();
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:"+step);
            this.aimNum.setText("aim:"+aim);
        }
        else if(choice==1){
            //TODO:hard
            step=8;
            allStep=step;
            aim=50;
            score=0;
            onPlayerRefresh();
            this.statusLabel.setText("Score:" + score/*+"\nthe step you have:"+step*/);
            this.theStepNumber.setText("step:"+step);
            this.aimNum.setText("aim:"+aim);
        }
        else if(choice==2){
            //TODO:customize
            int step0 = step;
            int aim0 = aim;
            String str1;
            String str2;
            try{
                str1 = JOptionPane.showInputDialog("Enter step");
                if(str1==null){
                }
                else {
                    step= Integer.parseInt(str1);
                    if(step<=0){
                        view.numError();
                        step=step0;
                    }
                    else {
                        str2 = JOptionPane.showInputDialog("Enter aim score");
                        if (str2==null){
                        }
                        else {
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
            }catch (NumberFormatException e){
                view.numError();
                step=step0;
                aim=aim0;
            }

        }
        else {
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

    public void setLabel(){
        this.statusLabel.setText("Score:" + score);
        this.theStepNumber.setText("step:"+step);
        this.aimNum.setText("aim:"+aim);
        this.toDO.setText("toDo:swap");
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

    public void onChangeMusic(){
        String[] options = {"Play", "Break"};
        int choice = JOptionPane.showOptionDialog(null,
                "Choose an option:",
                "Options",
                JOptionPane.NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if(choice==0){
            Main.getOp().backMusic.run();
        }
        else if(choice==1){
            if (Main.getOp().backMusic != null) {
                Main.getOp().backMusic.pauseMusic();
            }
        }
        else {

        }
    }
}
