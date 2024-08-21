package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


// listens to key inputs
public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // detects key pressed

        if(code == KeyEvent.VK_W) {
            upPressed=true;
        }
        if(code == KeyEvent.VK_A) {
            leftPressed=true;
        }
        if(code == KeyEvent.VK_S) {
            downPressed=true;
        }
        if(code == KeyEvent.VK_D) {
            rightPressed=true;
        }
        if(code == KeyEvent.VK_SPACE){
            //pause and unpause
            if(pausePressed){
                pausePressed= false;
                GamePanel.music.play(0,true);
                GamePanel.music.loop();
            } else {
                pausePressed = true;
                GamePanel.music.stop();
            }
        }

    }



    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
