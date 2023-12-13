package model;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initLoop(){
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
    }

    private void initPieces() {
        initLoop();
        while(isGoodInit()==false){
            initLoop();
        }
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }
    public void scanTheChessBoard(){     //Detect chess pieces that can be eliminated
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum()-3+1;j++){
                if(grid[i][j].isToRemoveRow()==true){continue;}
                ChessPiece currentPiece=grid[i][j].getPiece();
                ArrayList<Cell> rowPossibleRemoveList=new ArrayList<Cell>();
                rowPossibleRemoveList.add(grid[i][j]);
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_COL_SIZE.getNum();k++){
                    if(grid[i][k].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                        rowPossibleRemoveList.add(grid[i][k]);
                    }else {break;}
                }
                if(count>=3){
                    for(Cell a:rowPossibleRemoveList){
                        a.setToRemoveRow(true);
                    }
                }

            }

        }
        for(int i=0;i<Constant.CHESSBOARD_COL_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_ROW_SIZE.getNum()-3+1;j++){
                if(grid[j][i].isToRemoveCol()==true){continue;}
                ChessPiece currentPiece=grid[j][i].getPiece();
                ArrayList<Cell> colPossibleRemoveList=new ArrayList<Cell>();
                colPossibleRemoveList.add(grid[j][i]);
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_ROW_SIZE.getNum();k++){
                    if(grid[k][i].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                        colPossibleRemoveList.add(grid[k][i]);
                    }else {break;}
                }
                if (count>=3){
                    for(Cell a:colPossibleRemoveList){
                        a.setToRemoveCol(true);
                    }
                }
            }
        }



    }



    public boolean CanSwap(ChessboardPoint point1,ChessboardPoint point2){     //检测能不能换？
        Cell[][] temp = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                temp[i][j] = grid[i][j];
            }
        }
        var p1 = temp[point1.getRow()][point1.getCol()].getPiece();
        var p2 = temp[point2.getRow()][point2.getCol()].getPiece();
        temp[point1.getRow()][point1.getCol()].setPiece(p2);
        temp[point2.getRow()][point2.getCol()].setPiece(p1);
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum()-3+1;j++){
                ChessPiece currentPiece=temp[i][j].getPiece();
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_COL_SIZE.getNum();k++){
                     if(temp[i][k].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                    }else {break;}
                }
                if(count>=3){
                    temp[point1.getRow()][point1.getCol()].setPiece(p1);
                    temp[point2.getRow()][point2.getCol()].setPiece(p2);
                    return true;
                }

            }

        }
        for(int i=0;i<Constant.CHESSBOARD_COL_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_ROW_SIZE.getNum()-3+1;j++){
                ChessPiece currentPiece=temp[j][i].getPiece();
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_ROW_SIZE.getNum();k++){
                    if(temp[k][i].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                    }else {break;}
                }
                if (count>=3){
                    temp[point1.getRow()][point1.getCol()].setPiece(p1);
                    temp[point2.getRow()][point2.getCol()].setPiece(p2);
                    return true;
                }
            }
        }
        temp[point1.getRow()][point1.getCol()].setPiece(p1);
        temp[point2.getRow()][point2.getCol()].setPiece(p2);
        return false;


    }

    public boolean isGoodInit(){   //初始化和不合规
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum()-3+1;j++){
                ChessPiece currentPiece=grid[i][j].getPiece();
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_COL_SIZE.getNum();k++){
                    if(grid[i][k].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                    }else {break;}
                }
                if(count>=3){
                    return false;
                }

            }

        }
        for(int i=0;i<Constant.CHESSBOARD_COL_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_ROW_SIZE.getNum()-3+1;j++){
                ChessPiece currentPiece=grid[j][i].getPiece();
                int count=1;
                for(int k=j+1;k<Constant.CHESSBOARD_ROW_SIZE.getNum();k++){
                    if(grid[k][i].getPiece().getName().equals(currentPiece.getName())){
                        count++;
                    }else {break;}
                }
                if (count>=3){
                    return false;
                }
            }
        }
        return true;
    }





















    public int basicCountPoint(){      //在这个方法下消去一个得到一分one point for each
        int point=0;
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].isToRemoveRow()==true|grid[i][j].isToRemoveCol()==true){
                    point++;
                }
            }
        }
        return point;
    }
    public Cell[][] getGrid() {
        return grid;
    }
    public void setToDefault(){   //把是否消去恢复默认状态
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                grid[i][j].setToRemoveRow(false);
                grid[i][j].setToRemoveCol(false);
            }
        }
    }

    public void basicElimilation(){             //基本消去法
        for(int i=0;i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++){
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(grid[i][j].isToRemoveRow()==true|grid[i][j].isToRemoveCol()==true){
                    grid[i][j].removePiece();
                }
            }
        }

    }





}
