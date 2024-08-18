package main;

// handles drawing UI, setting tetrominoes, handles gamplay actions (deleting lines, adding scores etc)

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    //Main Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;

    //Next Mino
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;

    //Effect (when deleting line)
    boolean effectCounterOn;
    int effectCounter = 0;
    ArrayList<Integer> effectY = new ArrayList<>();

    //Game Over
    boolean gameOver;

    //Scoring System:
    int level = 1;
    int lines = 0;
    int score = 0;


    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // line 's y

    //Others
    public static int dropInterval = 60;

    public PlayManager() {

        // Main Play Area Frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); //1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE; // center of the play area
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        //Set current Mino and starting position
        currentMino = this.randomMino();
        currentMino.setXY(MINO_START_X,MINO_START_Y);

        //Set next Mino and starting position
        nextMino = this.randomMino();
        nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);

    }

    public void update() {
        if(!currentMino.active && !gameOver ){
            //if Mino is not active, put it into static Blocks Array (its deactivated on bottom collision)
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            checkGameOver();

            currentMino.deactivating = false;

            //replace current Mino with next Mino
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X,MINO_START_Y);
            nextMino = randomMino();
            nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);

            //when Mino becomes inactive, check if Line to delete
            checkForLine();

        }else {
            currentMino.update();
        }
    }

    private Mino randomMino() {
        //Generate a random Mino
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch(i){
            case 0 : mino =  new Mino_L1(); break;
            case 1 : mino = new Mino_L2(); break;
            case 2 : mino = new Mino_Square(); break;
            case 3 : mino = new Mino_Bar(); break;
            case 4 : mino = new Mino_T(); break;
            case 5 : mino = new Mino_Z1(); break;
            case 6 : mino = new Mino_Z2(); break;
        }
        return mino;
    }
    public  void checkForLine() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        //scan through the play area
        while(x< right_x && y < bottom_y){

            for(int i = 0; i< staticBlocks.size(); i++){
                if(staticBlocks.get(i).x==x && staticBlocks.get(i).y ==y){
                    blockCount++;
                }
            }
            x+=Block.SIZE;
            if(x==right_x){

                //if block count is 12, it means the line is filled with blocks, so we can delete the line
                if(blockCount == 12){
                    effectCounterOn = true;
                    this.effectY.add(y);
                    
                    // have to remove from descending order as index shifts everytime it move so will create weird result
                    for(int i = staticBlocks.size()-1; i>-1; i--){
                        if(staticBlocks.get(i).y == y){
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;
                    //increase drop speed each time when deleting 10 lines
                    if(lines%10 == 0 && dropInterval > 1){
                        level++;
                        if(dropInterval>10){
                            dropInterval-=10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    // a line has been deleted so shift all remaining blocks down by one block size
                    for(int i = 0; i < staticBlocks.size(); i++){
                        if(staticBlocks.get(i).y< y){
                            staticBlocks.get(i).y+=Block.SIZE;
                        }
                    }

                    //multiLine Score
                    if(lineCount>0){
                        int singleLineScore = level*10;
                        score += singleLineScore * lineCount;
                    }



                }
                blockCount = 0;
                x=left_x;
                y+=Block.SIZE;

            }
        }
    }

    public void checkGameOver(){
        //if Mino does not move from starting position, it means there is no space left so Game over true
        if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y){
            gameOver = true;
        }
    }


    public void draw(Graphics2D g2) {
    //Draw Play Area Frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x -4, top_y-4, WIDTH+8, HEIGHT + 8);

        //Draw Next Mino Frame
        int x  = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x,y, 200,200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+40, y+60);

        //draw score frame
        g2.drawRect( x  ,50, 240,260);
        g2.setFont(g2.getFont().deriveFont(32f));
        g2.drawString("Level:  " + level, x+ 40,  120 );
        g2.drawString("Line:   " + lines, x+ 40, 170 );
        g2.drawString("Score: " + score, x+ 40, 220 );

        //Draw the currentMino
        if(currentMino != null){
            currentMino.draw(g2);
        }
        //Draw the next Mino
        nextMino.draw(g2);

        //Draw Static Blocks Array
        for (int i = 0; i <staticBlocks.size(); i++){
            staticBlocks.get(i).draw(g2);
        }

        //Draw effects
        if(effectCounterOn){
            effectCounter ++;
            g2.setColor(Color.white);
            for(int i= 0; i< effectY.size();i++){
                g2.fillRect(left_x,effectY.get(i), WIDTH,Block.SIZE);
            }

            if(effectCounter == 10){
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        //Draw PAUSED TEXT if SpaceBar is pressed
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if(gameOver){
            g2.setColor(Color.RED);
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("GameOver!",x,y);
        } else if(KeyHandler.pausePressed == true){
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED",x,y);
        }
    }
}
