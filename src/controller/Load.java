package controller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import model.*;
public class Load {

    public static GameController loadGameController() {
        GameController gc = null;
        try {
            FileInputStream fileInputStream = new FileInputStream("gameController.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            // 从文件加载 Chessboard 对象
            gc = (GameController) objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gc;
    }
}
