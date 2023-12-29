package view;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
import javazoom.jl.player.*;
public class MusicPlayer implements Runnable{
    Player player;
    Player player1;
    Player player2;
    public static void playMusic(String name) {

    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/view/music1.mp3");
            player = new Player(fileInputStream);


            // 播
            player.play();

            // 关
            fileInputStream.close();
            FileInputStream fileInputStream1 = new FileInputStream("src/view/music.mp3");
            player1 = new Player(fileInputStream1);
            player1.play();
            fileInputStream1.close();
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
//        player.close();
//        System.out.println("Start");
//        try {
//            // 让当前线程暂停5秒钟
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("End after 0.5 seconds");
//        player1.close();
//        try {
//            // 让当前线程暂停5秒钟
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("End after 0.5 seconds");
//        player2.close();
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
