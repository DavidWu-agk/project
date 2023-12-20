package controller;

import model.Chessboard;

import javax.swing.*;
import java.io.*;

public class Load {
    public static GameController loadController() {
        GameController gameController = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load Game"); // 设置对话框标题

        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            try (FileInputStream fileIn = new FileInputStream(selectedFile);
                 ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                gameController = (GameController) objectIn.readObject();
                System.out.println("Game has been loaded from: " + selectedFile.getAbsolutePath());

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return gameController; // Return the loaded game controller
    }
}
