package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import controller.GameController;
import view.*;
import model.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class OpeningPage extends JFrame {
   private int WIDTH;
   private int HEIGHT;
    public String [] filePath=new String[]{"src/view/output1.png","src/view/output2.png","src/view/output3.png"};
    public String path=null;
   public OpeningPage(int width, int height) {
       this.WIDTH = width;
       this.HEIGHT = height;
       setSize(WIDTH, HEIGHT);
       setLocationRelativeTo(null);
       setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

       // 创建一个图层面板（Layered Pane）
       JLayeredPane layeredPane = new JLayeredPane();
       setContentPane(layeredPane);
       Random random = new Random();
       path=filePath[random.nextInt(3)];
       // 创建一个背景面板来显示背景图片
       JPanel backgroundPanel = new JPanel() {
           @Override
           protected void paintComponent(Graphics g) {
               super.paintComponent(g);
               try {
                   // 读取背景图片并绘制在面板上
                   Image backgroundImage = ImageIO.read(new File(path)); // 用你的图片路径替换此处路径
                   g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       };
       backgroundPanel.setBounds(0, 0, WIDTH, HEIGHT);
       layeredPane.add(backgroundPanel, JLayeredPane.DEFAULT_LAYER);

       JPanel buttonPanel = new JPanel();
       buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
       addStartButton(buttonPanel);
        addLoadButton(buttonPanel);
        addChangeThemeButton(buttonPanel);
       buttonPanel.setOpaque(false); // 使按钮面板透明，以便显示背景图片

       // 设置按钮面板的位置和大小
       buttonPanel.setBounds((WIDTH - 200) / 2, (HEIGHT - 80 * 3 - 20 * 2) / 2+200, 200, 80 * 3 + 20 * 2);
       layeredPane.add(buttonPanel, JLayeredPane.POPUP_LAYER); // 将按钮面板添加到图层面板中

       validate(); // 重新验证布局

        }






















            private void addStartButton(JPanel p) {
        JButton button = new JButton("Start");
        button.addActionListener(e -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setStatusLabel(mainFrame.getStatusLabel());
            mainFrame.setVisible(true);
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFont(new Font("Rockwell", Font.BOLD, 24));
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        p.add(button);
    }
    private void addLoadButton(JPanel p) {
        JButton button = new JButton("Load");
        button.addActionListener(e -> {
            //do later
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 80));
        button.setFont(new Font("Rockwell", Font.BOLD, 24));
        button.setMargin(new Insets(10, 10, 10, 10));
        button.setVerticalTextPosition(SwingConstants.CENTER);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        p.add(button);
    }
    public void addChangeThemeButton(JPanel p){
        JButton openOptionsButton = new JButton("Theme");
        p.add(openOptionsButton);
        openOptionsButton.addActionListener(e -> {
            String[] options = {"Option 3", "Option 2", "Option 1"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Choose an option:",
                    "Options",
                    JOptionPane.NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[2]);

            // 处理用户选择
            if (choice >= 0) {
                path=filePath[choice];
                repaint();
            }
        });
        openOptionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        openOptionsButton.setPreferredSize(new Dimension(200, 80));
        openOptionsButton.setFont(new Font("Rockwell", Font.BOLD, 24));
        openOptionsButton.setMargin(new Insets(10, 10, 10, 10));
        openOptionsButton.setVerticalTextPosition(SwingConstants.CENTER);
        openOptionsButton.setHorizontalTextPosition(SwingConstants.CENTER);
    }



}

