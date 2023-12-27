package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {

    private int deltaStep=0;
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private GameController gameController;

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece));
                }
            }
        }

    }

    public void initiateGridComponents() {

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public void removeAllChessComponentsAtGrids(){
        //todo:  complete the method
    }


    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = null;
        if(getGridComponentAt(point).getComponents().length!=0) {
            chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
            getGridComponentAt(point).removeAll();
            getGridComponentAt(point).revalidate();
            chess.setSelected(false);
        }
        else {
        }
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess(){
        gameController.onPlayerSwapChess();
    }

    public void nextStep(){
        gameController.onPlayerNextStep();
    }
    public void refresh(){ gameController.onPlayerRefresh();}
    public void restart(){
        gameController.onPlayerRestart();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
    public void cantSwap(){
        JOptionPane.showMessageDialog(this, "Can't swap.");
    }
    public void numError(){
        JOptionPane.showMessageDialog(this, "Not a positive integer.");
    }
    public void stepOut(){JOptionPane.showMessageDialog(this,"Game finish.");}

    public void winOrLose(){
        if(gameController.getScore()>=gameController.getAim()){

            String[] options = {"Next level", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "You win!",
                    null,
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if(choice==0){
                gameController.setAim(gameController.getAim()+10);
                gameController.onPlayerRestart();
                gameController.getFr().getExecutor().shutdown();
                //System.out.println("to stop");
                gameController.getFr().getExecutor().shutdownNow();
            }
            else {
                gameController.getFr().getExecutor().shutdown();
                //System.out.println("to stop");
                gameController.getFr().getExecutor().shutdownNow();
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"You lose.");
            gameController.onPlayerRestart();
            gameController.setSelectedPoint(null);
            gameController.setSelectedPoint2(null);
            gameController.getFr().getExecutor().shutdown();
            gameController.getFr().getExecutor().shutdownNow();

        }
    }

    public void chooseLevel(){gameController.onChooseLevel();}

    public int getDeltaStep() {
        return deltaStep;
    }

    public void setDeltaStep(int deltaStep) {
        this.deltaStep = deltaStep;
    }


}
