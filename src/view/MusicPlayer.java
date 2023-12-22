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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
