package mino;

import java.awt.Color;

public class Mino_Z1 extends Mino{

    public Mino_Z1() {
        create(Color.red);
    }

    public void setXY(int x, int y) {

        // o o        [b2][b1]
        //   o o          [b0] [b3]
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;       //updates based on the axis
        b[1].y = b[0].y  - Block.SIZE;
        b[2].x = b[0].x -  Block.SIZE;
        b[2].y = b[0].y  - Block.SIZE ;
        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y;
    }


    public void getDirection1() {
        // o o        [b2][b1]
        //   o o          [b0] [b3]
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(1);
    }

    public void getDirection2 () {
        //   o
        // o o
        // o
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x- Block.SIZE;
        tempB[1].y = b[0].y ;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.SIZE;;

        updateXY(2);
    }
    public void getDirection3() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Block.SIZE;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y - Block.SIZE;
        tempB[3].x = b[0].x + Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(3);
    }
    public void getDirection4() {

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x- Block.SIZE;
        tempB[1].y = b[0].y ;
        tempB[2].x = b[0].x - Block.SIZE;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y - Block.SIZE;;

        updateXY(4);
    }
}
