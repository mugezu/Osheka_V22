package Interface;

import Logic.Matrix;
import Exception.ExceptionSize;
import javax.swing.*;
import java.awt.*;
import java.util.Date;

/**
 * Created by Roman on 17.03.2017.
 */
public class DrawThread implements Runnable {
    int[][] matrix;
    int[][] A;
    int[][] B;
    int P;
    int T;
    Graphics g;
    Container container;
    boolean flag;
    StringBuffer json = new StringBuffer();

    public DrawThread(int[][] matrix, int[][] A, int[][] B, int P, Container container,int T) {
        this.matrix = matrix;
        this.A = A;
        this.B = B;
        this.P = P;
        flag = true;
        this.container = container;
        this.g = container.getGraphics();
        this.T=T;
    }

    @Override
    public void run() {
        int[][] matrixRes;
        JLabel label = (JLabel) container.getComponent(1);
        container.getGraphics().clearRect(0, 50, 600, 600);
        json.append("Matrix " + matrix.length + "x" + matrix[0].length).append(" GF(" + P + ")" + System.lineSeparator());
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
                Matrix.drawMatrix(g, matrix, 100, 600, 100);
                label.setText("Итерация: " + i + "  Время выполнения: " + (double) (System.currentTimeMillis() - s) / 1000+" Кадров в секунду: "+(int)(i/((double)(System.currentTimeMillis() - s) / 1000))+" Период(экс)="+T);
                i++;
            }
        } catch (ExceptionSize exceptionSize) {
            exceptionSize.printStackTrace();
            return;
        }
    }
}