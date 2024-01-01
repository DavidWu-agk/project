package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant1 {
    CHESSBOARD_ROW_SIZE1(6),CHESSBOARD_COL_SIZE1(6);


    private final int num1;
    Constant1(int num1){
        this.num1 = num1;
    }

    public int getNum1() {
        return num1;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
        put("😅",Color.blue);
        put("😍",Color.red);
        put("😋",Color.green);
        put("😡",Color.yellow);
        put("-",Color.gray);
    }};

}
