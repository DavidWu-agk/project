package controller;

import model.Chessboard;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Save {
    public static void saveGame(GameController gameController) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Game"); // 设置对话框标题

        // 设置默认文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date now = new Date();
        String timeStamp = formatter.format(now);
        String defaultFileName = "game_" + timeStamp + ".ser";
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            try (FileOutputStream fileOut = new FileOutputStream(fileToSave);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

                objectOut.writeObject(gameController);
                System.out.println("Game has been saved to: " + fileToSave.getAbsolutePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
