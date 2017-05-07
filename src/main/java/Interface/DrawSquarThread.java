package Interface;

import Logic.Matrix;

import java.awt.*;

/**
 * Created by Roman on 17.03.2017.
 */
public class DrawSquarThread implements Runnable {
    Graphics g;
    int x;
    int y;
    int size;
    int c;

    public DrawSquarThread(Graphics g, int x, int y, int size,int c) {
        this.g = g;
        this.x = x;
        this.y = y;
        this.size = size;
        this.c=c;
    }

    @Override
    public void run() {
        Matrix.drawSquare(g,x,y,size,c);
    }
}
