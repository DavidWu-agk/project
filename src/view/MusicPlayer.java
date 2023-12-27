package view;
import javazoom.jl.player.Player;
import java.io.FileInputStream;
public class MusicPlayer implements Runnable{
    public static void playMusic(String name) {

    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream("src/view/music.mp3");
            Player player = new Player(fileInputStream);


            // 播
            player.play();

            // 关
            fileInputStream.close();
            FileInputStream fileInputStream1 = new FileInputStream("src/view/music1.mp3");
            Player player1 = new Player(fileInputStream1);
            player1.play();

            // 关
            fileInputStream1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
