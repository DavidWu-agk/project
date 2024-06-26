package model;


import java.awt.*;
import java.io.Serializable;

public class ChessPiece implements Serializable {
    // Diamond, Circle, ...
    private String name;

    private Color color;

    public ChessPiece(String name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

}
