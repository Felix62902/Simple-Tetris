package mino;

import java.awt.Color;

public class Mino_Bar extends Mino{

    public Mino_Bar() {
        create(Color.cyan);
    }

    public void setXY(int x, int y) {
        // o   b[1]
        // o  b[0]
        // o    b[2]
        // o    b[3]
        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;       //updates based on the axis
        b[1].y = b[0].y - Block.SIZE;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;
        b[3].x = b[0].x ;
        b[3].y = b[0].y + Block.SIZE + Block.SIZE;
    }

    public void getDirection1() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y- Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y+ Block.SIZE+ Block.SIZE;

        updateXY(1);
    }

    public void getDirection2() {
        //oooo
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x -  Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x  - Block.SIZE  - Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(2);
    }

    public void getDirection3() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y- Block.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Block.SIZE;
        tempB[3].x = b[0].x;
        tempB[3].y = b[0].y+ Block.SIZE+ Block.SIZE;

        updateXY(3);
    }

    public void getDirection4() {
        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + + Block.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x -  Block.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x  - Block.SIZE  - Block.SIZE;
        tempB[3].y = b[0].y;

        updateXY(4);
    }
}
