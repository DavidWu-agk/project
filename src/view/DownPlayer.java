package view;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class DownPlayer implements Runnable{
    Player player;
    public static void playMusic(String name) {

    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/view/downmusic.m4a");
            player = new Player(fileInputStream);// 播
            player.play();// 关
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void breakMusic(){

    }
}
