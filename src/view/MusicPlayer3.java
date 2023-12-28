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

    public void run1() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/view/music1.mp3");
            player = new Player(fileInputStream);


            // 播
            player.play();

            // 关
            fileInputStream.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run2() {
        try {

            // 关

            FileInputStream fileInputStream1 = new FileInputStream("src/view/music.mp3");
            player1 = new Player(fileInputStream1);
            player1.play();
            fileInputStream1.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run3() {
        try {

            // 关

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
        try {
            // 让当前线程暂停5秒钟
            player.close();
            System.out.println("Start");
            Thread.sleep(500);
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 让当前线程暂停5秒钟
            player1.close();
            System.out.println("Start");
            Thread.sleep(500);
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
        try {
            // 让当前线程暂停5秒钟
            player2.close();
            System.out.println("Start");
            Thread.sleep(500);
        } catch (NullPointerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
