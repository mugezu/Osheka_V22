package Interface;

import Logic.Matrix;
import Exception.ExceptionSize;
import Logic.WorkQueue;

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
    static WorkQueue workQueue;

    public DrawThread(int[][] matrix, int[][] A, int[][] B, int P, Container container) {
        this.matrix = matrix;
        this.A = A;
        this.B = B;
        this.P = P;
        flag = true;
        this.container = container;
        this.g = container.getGraphics();
        workQueue = new WorkQueue(5);
    }

    @Override
    public void run() {
        int[][] matrixRes;
        JLabel label = (JLabel) container.getComponent(1);
        container.getGraphics().clearRect(0, 50, container.getWidth(), container.getHeight());
        int i = 0;
        long s = System.currentTimeMillis();
        try {
            while (i < 150) {
                matrixRes = Logic.Matrix.multiplication(A, matrix, 7);
                matrix = Logic.Matrix.multiplication(matrixRes, B, 7);
               // Matrix.drawMatrixThread(g, matrix, 100, 600, 100, workQueue);
                  Matrix.drawMatrix(g, matrix, 100, 600, 100);
                label.setText("Итерация: " + i + "  Время выполнения: " + (double) (System.currentTimeMillis() - s) / 1000);
                i++;
            }
        } catch (ExceptionSize exceptionSize) {
            exceptionSize.printStackTrace();
            return;
        }
    }
}