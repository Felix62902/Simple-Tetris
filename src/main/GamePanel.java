package main;

import java.awt.*;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread; // to run gameLoop ( must use Runnable interface)
    public PlayManager pm;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);  // no layout preset, therefore customizable
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true); //get key whenever the window is focused

        pm = new PlayManager();
    }

    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start(); // start automatically calls the run method
    }

    //Game Loop, in every game loop, we do two things, update and draw
    @Override
    public void run() {
        //Game Loop
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread!= null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta>=1){
                // calls update and repaint 60 times per second
                update();
                repaint(); // calls paintComponent method
                delta--;
            }
        }
    }

    private void update() {
        if(KeyHandler.pausePressed == false && pm.gameOver == false){
            pm.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g; //converts graphics to 2D
        pm.draw(g2);
    }
}
