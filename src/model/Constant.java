package model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public enum Constant {//棋盘尺寸、棋子样式、与num（？）
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);//棋盘尺寸

    private final int num;
    Constant(int num){
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
        put("💎",Color.blue);
        put("⚪",Color.white);
        put("▲",Color.green);
        put("🔶",Color.orange);
    }};

}
