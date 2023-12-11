package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

public interface GameListener {
    //监视点击等行为

    void onPlayerClickCell(ChessboardPoint point, CellComponent component);


    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);

    public void onPlayerSwapChess();

    public void onPlayerNextStep();

}
