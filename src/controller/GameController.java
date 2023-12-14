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

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;


    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;

    private int score;

    private JLabel statusLabel;

    public JLabel getStatusLabel() {
        return statusLabel;
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
        if(selectedPoint!=null && selectedPoint2!=null&&model.CanSwap(selectedPoint,selectedPoint2)==true){
            model.swapChessPiece(selectedPoint,selectedPoint2);//这是调用了model层的方法，model层是project的底层，有着各种判断棋子间关系与胜负等的方法
            ChessComponent chess1= view.removeChessComponentAtGrid(selectedPoint);
            ChessComponent chess2= view.removeChessComponentAtGrid(selectedPoint2);
            view.setChessComponentAtGrid(selectedPoint2,chess1);//这是对view层进行了修改，model层改变后，还需要把变化导入到view层并repaint才可视化
            view.setChessComponentAtGrid(selectedPoint,chess2);
            chess1.repaint();
            chess2.repaint();
            model.scanTheChessBoard();
            score=score+model.basicCountPoint();
            chessComponentBasicElimilation();
            model.basicElimilation();
            model.setToDefault();
        }

        System.out.println("Implement your swap here.");

    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        System.out.println("Implement your next step here.");
        this.statusLabel.setText("Score:" + score);
        chessDownInGameController();
    }

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
                    ChessComponent noChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece("-"));
                    ChessboardPoint p=new ChessboardPoint(i,j);
                    view.removeChessComponentAtGrid(p);
                    view.setChessComponentAtGrid(p,noChess);
                    noChess.repaint();
                }
            }
        }
    }

    public void chessDownInGameController(){
        model.chessDown();
        for(int i=Constant.CHESSBOARD_ROW_SIZE.getNum()-1;i>=0;i--) {
            for (int j = Constant.CHESSBOARD_COL_SIZE.getNum()-1; j >= 0; j--) {
                ChessboardPoint p=new ChessboardPoint(i,j);
                ChessComponent thisChess;
                if(model.getGridAt(p).getPiece()!=null){
                    thisChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece(model.getGridAt(p).getPiece().getName()));//MAYBE NULL
                }
                else {
                    thisChess=new ChessComponent(view.getCHESS_SIZE(),new ChessPiece("-"));
                }
                view.removeChessComponentAtGrid(p);
                view.setChessComponentAtGrid(p,thisChess);
                thisChess.repaint();
                //System.out.printf("114514");
            }
        }
    }
}
