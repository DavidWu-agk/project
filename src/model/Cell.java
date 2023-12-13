package model;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell implements Serializable {
    // the position for chess
    private ChessPiece piece;
    private boolean isToRemoveRow=false;
    private boolean isToRemoveCol=false;
    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;

    }

    public void removePiece() {
        this.piece = null;
    }

    public boolean isToRemoveRow() {
        return isToRemoveRow;
    }

    public boolean isToRemoveCol() {
        return isToRemoveCol;
    }

    public void setToRemoveCol(boolean toRemoveCol) {
        isToRemoveCol = toRemoveCol;
    }

    public void setToRemoveRow(boolean toRemove) {
        isToRemoveRow = toRemove;
    }
}
