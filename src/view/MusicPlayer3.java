package view;

import javazoom.jl.player.Player;

import java.io.FileInputStream;

public class MusicPlayer3 extends MusicPlayer implements Runnable{
    Player player;
    Player player1;
    Player player2;
    public static void playMusic(String name) {

    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream2 = new FileInputStream("src/view/music2.mp3");
            player2 = new Player(fileInputStream2);
            player2.play();
            // 关
            fileInputStream2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void pauseMusic(){
        if(player!=null){
            player.close();
        }
        try {
            // 让当前线程暂停5秒钟

            System.out.printf("test111");
            System.out.println("Start");
            Thread.sleep(50);
        } catch (NullPointerException | InterruptedException e) {
            System.out.printf("test113");
            e.printStackTrace();
        }
        if(player1!=null){
            player1.close();
        }
        try {
            // 让当前线程暂停5秒钟
            System.out.println("Start");
            Thread.sleep(50);
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        if(player2!=null){
            player2.close();
        }
        try {
            // 让当前线程暂停5秒钟
            System.out.println("Start");
            Thread.sleep(50);
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
