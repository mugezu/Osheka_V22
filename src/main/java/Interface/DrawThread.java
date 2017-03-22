package Interface;

import Logic.Matrix;
import Exception.ExceptionSize;
import Logic.WorkQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

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
    StringBuffer json = new StringBuffer();

    public StringBuffer getJson() {
        return json;
    }

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
        json.append("Matrix "+matrix.length+"x"+matrix[0].length).append(" GF("+P+")"+System.lineSeparator());
        int i = 0;
        long s = System.currentTimeMillis();
        try {
            while (true) {
                matrixRes = Logic.Matrix.multiplication(A, matrix, 7);
                matrix = Logic.Matrix.multiplication(matrixRes, B, 7);
                Date date = new Date(System.currentTimeMillis());
                String a = Matrix.toString(matrix);
                json.append(i + ": " + date + System.lineSeparator());
                json.append(a);
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