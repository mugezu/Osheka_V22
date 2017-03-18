package Interface;

import Logic.Matrix;

import java.awt.*;

/**
 * Created by Roman on 17.03.2017.
 */
public class DrawSquareThread implements Runnable {
    Graphics g;
    int x;
    int y;
    int size;
    int value;

    public DrawSquareThread(Graphics g, int x, int y, int size,int value) {
        this.g = g;
        this.x = x;
        this.y = y;
        this.size = size;
        this.value = value;
    }

    @Override
    public void run() {
        Matrix.drawSquare(g,x,y,size,value);
    }
}
