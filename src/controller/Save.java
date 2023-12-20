package controller;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import model.*;
public class Save {

    public static void saveGameController(GameController gameController) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("gameController.ser");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameController);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

