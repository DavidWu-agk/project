package model;


import java.awt.*;

public class ChessPiece {//只有样式，没有位置
    // Diamond, Circle, ...
    private String name;

    private Color color;

    public ChessPiece(String name) {
        this.name = name;//棋子名字
        this.color = Constant.colorMap.get(name);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

}
