package controller;

import listener.GameListener;
import model.ChessPiece;
import model.Constant;
import model.Chessboard;
import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;
import view.ChessGameFrame;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;

import static java.awt.AWTEventMulticaster.add;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {
    private int step=10;

    private Chessboard model;
    private ChessboardComponent view;
    private int nextstepCount=0;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;

    private JLabel statusLabel;

    private JLabel theStepNumber;

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
            this.theStepNumber.setText("the step you have:"+step);
            chessComponentBasicElimilation();
            model.basicElimilation();
            model.setToDefault();
            selectedPoint=null;
            selectedPoint2=null;//修复消了以后无法自由点击格子的bug
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
            //view.setDeltaStep(0);
        }

        System.out.println("Implement your swap here.");

    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        System.out.println("Implement your next step here.");
        if(nextstepCount%3==0){
            model.chessDown();
            sync();
            nextstepCount++;
        } else if (nextstepCount%3==1) {
            model.generate();
            sync();
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
        nextstepCount++;
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
        this.statusLabel.setText("Score:" + score);
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

    public void setLabel(){
        this.statusLabel.setText("Score:" + score);
        this.theStepNumber.setText("the step you have:"+step);
    }

}
