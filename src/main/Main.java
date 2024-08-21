package main;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Add GamePanel to window
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack(); // the size of gamePanel becomes size of window

        window.setLocationRelativeTo(null);  // so window is in middle
        window.setVisible(true); //else we cant see the window\

        gp.launchGame();
    }
}