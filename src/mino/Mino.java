package mino;
import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

//superclass for all Tetrominoes, they all extend this class
public class Mino {

    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4]; // temporary stores the new mino position after rotation
    int autoDropCounter = 0;
    public int direction = 1; //there are 4 different direcitons (1 , 2, 3, 4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
     int deactivateCounter = 0;

    //instantiate Block Array by setting their colors
    public void create(Color c) {
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y){}

    public void updateXY(int direction){
        checkRotationCollision();
        if(!leftCollision && !rightCollision && !bottomCollision){
            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }

    }
    public void getDirection1() {} //will be overwritten in individual mino classes
    public void getDirection2 () {}
    public void getDirection3() {}
    public void getDirection4() {}

    public void checkMovementCollision(){
        //init/ reset all collision to false
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;


        //check static Block Collision
        this.checkStaticBlockCollision();
        //Check frame Collision
        //Left Wall
        for(int i = 0; i < b.length; i++){
            if (b[i].x == PlayManager.left_x){
                leftCollision = true;
            }
        }

        // Right Wall
        for(int i = 0; i < b.length; i++){
            if (b[i].x + Block.SIZE == PlayManager.right_x){
                rightCollision = true;
            }
        }

        //Bottom Wall
        for(int i = 0; i < b.length; i++){
            if (b[i].y + Block.SIZE == PlayManager.bottom_y){
                bottomCollision = true;
            }
        }

    }

    public void checkRotationCollision() {
        //init/ reset all collision to false
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        //check static Block Collision
        this.checkStaticBlockCollision();

        //Check frame Collision
        //Left Wall
        for(int i = 0; i < tempB.length; i++){
            if (tempB[i].x < PlayManager.left_x){
                leftCollision = true;
            }
        }

        // Right Wall
        for (int i = 0; i < tempB.length; i++){
            if (tempB[i].x + Block.SIZE > PlayManager.right_x){
                rightCollision = true;
            }
        }

        //Bottom Wall
        for(int i = 0; i < tempB.length; i++){
            if (tempB[i].y + Block.SIZE > PlayManager.bottom_y){
                bottomCollision = true;
            }
        }
    }

    public void checkStaticBlockCollision() {
        //Looping through all static Blocks
        for(int i = 0; i< PlayManager.staticBlocks.size(); i++) {
            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            //check down collision ( looping through all Minoes' blocks)
            for (int d = 0; d < b.length; d++){
                if(b[d].y + Block.SIZE == targetY && b[d].x == targetX){
                    bottomCollision = true;
                }
            }

            //check left
            for(int l = 0; l < b.length; l++){
                if(b[l].x - Block.SIZE == targetX && b[l].y == targetY){
                    leftCollision = true;
                }
            }
            //check right collision
            for(int r = 0; r < b.length; r++){
                if(b[r].x + Block.SIZE == targetX && b[r].y == targetY){
                    rightCollision = true;
                }
            }



        }
    }

    public void update() {
        if(deactivating== true){
            deactivating();
        }



        //handles rotation
        if(KeyHandler.upPressed) {
            switch(direction){
                case 1: getDirection2(); break; //if current direction is one, call direction 2 method
                case 2: getDirection3(); break;
                case 3: getDirection4(); break;
                case 4: getDirection1(); break;
            }
            KeyHandler.upPressed = false;
        }

        //before handling left right down movement, check if Mino is touching Wall
        checkMovementCollision();

        if(KeyHandler.downPressed) {
            // if the mino's bottom is not hitting, it can go Down
            if(bottomCollision == false){
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                // when moved down, reset the autoDropCounter
                autoDropCounter = 0;
            }
            KeyHandler.downPressed = false;
        }

        //Tetris's move logic
        if(KeyHandler.leftPressed) {
            //if mino's left is not hitting the wall, it can go Left
            if(leftCollision==false){
                b[0].x -= Block.SIZE;
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }

            KeyHandler.leftPressed = false;
        }
        if(KeyHandler.rightPressed) {
            //if mino's right is not hitting the wall, it can go Left
            if (rightCollision == false){
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
                KeyHandler.rightPressed = false;
            }
        }

        if(bottomCollision){
            //if mino hits bottom, stop it from moving down
           deactivating();

        } else{
            //counter increases in every frame
            autoDropCounter++;
            // when the counter hits the draw interval, counter moves down by one block
            if(autoDropCounter == PlayManager.dropInterval){
                b[0].y += Block.SIZE;
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;
                autoDropCounter = 0;
            }
        }

    }

    public void deactivating() {
        deactivateCounter++;
        //wait 45 frames before deactivating
        if(deactivateCounter == 45){
            deactivateCounter = 0;
            checkMovementCollision();
            // if collision still happening after 45 frames, deactivate
            if (bottomCollision){
                active = false;
            }
        }


    }

    public void draw(Graphics2D g2){

        int margin = 2; // set gaps to distinguis between blocks

        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+ margin,b[0].y+ margin,Block.SIZE-(margin *2),Block.SIZE-(margin *2));
        g2.fillRect(b[1].x+ margin,b[1].y+ margin,Block.SIZE-(margin *2),Block.SIZE-(margin *2));
        g2.fillRect(b[2].x+ margin,b[2].y+ margin,Block.SIZE-(margin *2),Block.SIZE-(margin *2));
        g2.fillRect(b[3].x+ margin,b[3].y+ margin,Block.SIZE-(margin *2),Block.SIZE-(margin *2));
    }
}
