package Logic;

import Exception.ExceptionSize;
import java.awt.*;

/**
 * Created by Roman on 16.03.2017.
 */
public class Matrix {
     public static int size(int[][] matrix, int pixel) {
        int tmp = matrix[0].length;
        if (pixel % tmp < (pixel / tmp) / 2)
            pixel = pixel - (pixel % tmp);
        else pixel = pixel - (pixel % tmp) + tmp;
        return pixel / tmp;
    }

    public static Color color(int value) {
        switch (value) {
            case 0: {
                return Color.red;
            }
            case 1: {
                return Color.orange;
            }
            case 2: {
                return Color.yellow;
            }
            case 3: {
                return Color.green;
            }
            case 4: {
                return Color.blue;
            }
            case 5: {
                return new Color(0, 191, 255);
            }
            case 6: {
                return new Color(90, 0, 157);
            }
            default:
                return Color.black;

        }
    }

    public static void drawSquare(Graphics g, int x, int y, int size, int value) {
        g.setColor(color(value));
        for (int i = 0; i < size; i++) {
            g.drawLine(x, y + i, x + size, y + i);

        }
    }

  /*  public static void drawSquare(Graphics g, int x, int y, int size) {
        for (int i = 0; i < size; i++) {
            g.drawLine(x, y + i, x + size, y + i);

        }
    }

    public static void drawMatrixThread(Graphics g, int[][] matrix, int wightMin, int wightMax, int Height, WorkQueue workQueue) {
        int size = size(matrix, wightMax - wightMin);
        g.setColor(Color.red);
        for (int k = 0; k < 7 /*&& workQueue.isAliveAll()==true; ++k, g.setColor(color(k)))
            for (int i = 0; i < matrix.length; i++)
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == k) {

                        workQueue.execute(new DrawSquareThread(g, wightMin + j * size, Height + i * size, size, k));
                        //drawSquare(g,wightMin + j * size,Height + i * size,size/*,matrix[i][j]);
                    }
                }
    }
*/
    public static void drawMatrix(Graphics g, int[][] matrix, int wightMin, int wightMax, int Height) {
        int size = size(matrix, wightMax - wightMin);
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                drawSquare(g, wightMin + j * size, Height + i * size, size, matrix[i][j]);
            }
          /*  while (workQueue.isAliveAll()!=false){
                try {
                   Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        // workQueue.
    }

    public static String toString(int[][] matrix) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < matrix.length; i++, sb.append(System.lineSeparator()))
            for (int j = 0; j < matrix[i].length; j++) {
                sb.append(matrix[i][j] + " ");
            }
        return sb.toString();
    }

  /*  public static void Check(int[][] matrix) throws ExceptionSize, ExceptionValue {
        for (int i = 1; i < matrix.length; i++)
            if (matrix[i].length != matrix[i - 1].length) {
                throw new ExceptionSize("Матрица имеет неровные углы");
            }

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] >= 7) throw new ExceptionValue();
            }
    }

    public static void show(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.printf("%2d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    public static void CheckDouble(int[][] matrix, int[][] matrix2) throws ExceptionSize {
        if (matrix[0].length != matrix2.length) throw new ExceptionSize("Несоответствующий размер матриц");
    }
*/

    public static int[][] multiplication(int[][] a, int[][] b, int p) throws ExceptionSize {
        if (a == null || b == null) throw new ExceptionSize();
        int[][] c = new int[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b[i].length; j++)
                for (int k = 0; k < b.length; k++) {
                    c[i][j] = c[i][j] + (a[i][k] * b[k][j]) % p;
                    if (k + 1 == b.length) c[i][j] = c[i][j] % p;
                }
        return c;
    }
}
