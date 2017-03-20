package Interface;

import Logic.Matrix;
import Exception.ExceptionSize;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Roman on 17.03.2017.
 */
public class DrawThread implements Runnable {
    int[][] matrix;
    int[][] A;
    int[][] B;
    int P;
    Graphics g;
    Container container;
    boolean flag;

    public DrawThread(int[][] matrix,int[][] A, int [][]B, int P, Container container) {
        this.matrix = matrix;
        this.A=A;
        this.B=B;
        this.P = P;
        flag = true;
        this.container = container;
        this.g = container.getGraphics();
    }

    public void stop() {
        flag = false;
    }

    @Override
    public void run() {
        int[][] matrixRes;
        container.getGraphics().clearRect(50,50,700,500);
        try {
            while (true) {
                while (flag) {
                    matrixRes = Logic.Matrix.multiplication(matrix, A, 7);
                    matrix=Logic.Matrix.multiplication(matrixRes, B, 7);
                    Matrix.drawMatrix(g, matrix, 100, 600, 100);
          /*  long sek=24;
            long mil=1000;
            try {
                Thread.sleep(mil/sek);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
                }
            }
        } catch (ExceptionSize exceptionSize) {
            exceptionSize.printStackTrace();
            return;
        }
    }
}