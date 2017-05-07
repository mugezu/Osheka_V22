package Interface;

import Logic.FillingFile;
import Logic.Matrix;
import Exception.ExceptionSize;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Roman on 17.03.2017.
 */
public class SwingMain extends JFrame {
    private static Hashtable<Integer, String> table;
    private static int n;
    private JFileChooser fileChooser = null;
    private final static int P = 7;
    private static int[][] matrix;
    private static int[][] A;
    private static int[][] B;
    private static boolean flag = false;
    DrawThread drawThread;
    Thread t;
    String data;
    private static Checkbox checkbox;
    static int i = 0;

    public static void main(String[] args) {
        try {
            table = FillingFile.readFile();
        } catch (IOException e) {
            int reply = JOptionPane.showConfirmDialog(null, "Файл GF7.txt не найден", "Ошибка", JOptionPane.CLOSED_OPTION);
            e.printStackTrace();
            return;
        }
        try {
            new SwingMain();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SwingMain() throws HeadlessException, IOException {
        super();
        setTitle("Псевдослучайная генерация радуги");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addComponents(getContentPane());
        setPreferredSize(new Dimension(800, 700));
        pack();
        setVisible(true);
    }

    private void addComponents(Container contentPane) throws IOException {
        contentPane.setLayout(new BorderLayout());

        JToolBar toolBar = new JToolBar();
        fileChooser = new JFileChooser();
        contentPane.add(toolBar, BorderLayout.NORTH);
        JLabel jLabel = new JLabel();
        jLabel.setName("CountRefresh");
        contentPane.add(jLabel, BorderLayout.SOUTH);
        toolBar.add(createButtonWithMenu());
        // System.out.println(contentPane.getComponent(0).getName());
    }

    private JComponent createButtonWithMenu() {
        checkbox = new Checkbox("Пошаговая работа");
        final JButton fileButton = new JButton("Файл");
        final JPopupMenu menuFile = new JPopupMenu("Файл");
        ActionSaveFile actionSaveFile = new ActionSaveFile("Сохранить результат в файл", fileChooser, SwingMain.this);
        menuFile.add(actionSaveFile);
        fileButton.addActionListener(new ActionListenerAddList(menuFile, fileButton));
        final JButton menuButton = new JButton("Опции");
        final JPopupMenu menuFunction = new JPopupMenu("Опции");

        menuFunction.add(new AbstractAction("Ввести степень многочлена") {
            @Override
            public void actionPerformed(ActionEvent e) {
                i = 0;
                JTextField textArea = new JTextField();
                JLabel label = new JLabel();
                label.setText("Введите степень многочлена");
                JComponent[] inputs = {label, textArea};
                int replyi = JOptionPane.showConfirmDialog(null, inputs, "Разрядность", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (replyi == JOptionPane.OK_OPTION) {
                    System.out.println("OK");
                    n = Integer.valueOf(textArea.getText().toString());
                    GenerateMatrix(n);
                } else {
                    return;
                }
            }
        });
        final JButton showMSR = new JButton("Показать матрицу");
        final JPopupMenu showMSRMenu = new JPopupMenu("Показать матрицу");

        showMSRMenu.add(new AbstractAction("A") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMatrix(A,"Матрица А");
            }
        });
        showMSRMenu.add(new AbstractAction("В") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMatrix(B,"Матрица В");
            }
        });
        showMSRMenu.add(new AbstractAction("Текущего состояния") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMatrix(matrix,"Матрица текущего состояния");
            }
        });

        showMSR.addActionListener(new ActionListenerAddList(showMSRMenu, showMSR));

        menuFunction.add(new AbstractAction("Старт") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkbox.getState() == false)
                    if (flag == false) {
                        if (matrix != null) {
                            drawThread = new DrawThread(matrix, A, B, P, getContentPane());
                            t = new Thread(drawThread);
                            t.start();
                            flag = true;
                        }
                    }
            }
        });

        menuFunction.add(new AbstractAction("Стоп") {
            @Override
            public void actionPerformed(ActionEvent e) {
                action(actionSaveFile);
            }
        });


        JButton nextStep = new JButton("Следующй шаг");
        JLabel label = (JLabel) getContentPane().getComponent(1);
        StringBuffer json = new StringBuffer();
        nextStep.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkbox.getState() == true) {
                    int[][] matrixRes;
                    json.append("Matrix " + matrix.length + "x" + matrix[0].length).append(" GF(" + P + ")" + System.lineSeparator());
                    try {
                        matrixRes = Logic.Matrix.multiplication(A, matrix, P);
                        matrix = Logic.Matrix.multiplication(matrixRes, B, P);
                        Date date = new Date(System.currentTimeMillis());
                        json.append(i + ": " + date + System.lineSeparator());
                        json.append(Matrix.toString(matrix));
                        Matrix.drawMatrix(getGraphics(), matrix, 100, 600, 100);
                        label.setText("Итерация: " + i);
                        actionSaveFile.setData(json.toString());
                        i++;
                    } catch (ExceptionSize exceptionSize) {
                        exceptionSize.printStackTrace();
                        return;
                    }
                }
            }
        });

        checkbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getGraphics().clearRect(0, 70, 650, 600);
                if (checkbox.getState() == true) {
                    action(actionSaveFile);
                }
                Matrix.drawMatrix(getGraphics(), matrix, 100, 600, 100);
            }
        });
        menuFunction.add(checkbox);
        menuButton.addActionListener(new ActionListenerAddList(menuFunction, menuButton));
        final JButton helpButton = new JButton("Справка");
        final JPopupMenu helpFunction = new JPopupMenu("Справка");

        helpFunction.add(new AbstractAction("Об авторе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Автор продукта ст. Группи КИТ-64 \nОшека Роман\nВсе права защищены", "Об авторе", JOptionPane.CLOSED_OPTION);
            }
        });
        helpButton.addActionListener(new ActionListenerAddList(helpFunction, helpButton));
        JComponent res = new JComponent() {
        };
        res.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        res.add(fileButton);
        res.add(menuButton);
        res.add(showMSR);
        res.add(helpButton);
        res.add(nextStep);
        return res;
    }

    private void showMatrix(int[][] matrix, String name){
        JTextArea label = new JTextArea();
        label.setText(Matrix.toString(matrix) );
        JComponent[] inputs = {label};
        JOptionPane.showConfirmDialog(null, inputs, name, JOptionPane.OK_OPTION);
    }

    private void GenerateMatrix(int n) {
        Random random = new Random();
        String binKod = table.get(n);
        A = new int[n][n];
        B = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (i == 0) {
                    if (binKod != null)
                        A[i][j] = Integer.valueOf(binKod.substring(j, j + 1));
                    else A[i][j] = random.nextInt(P);
                }
                if (i - j == 1) {
                    A[i][j] = 1;
                }
                if (j == 0) {
                    if (binKod != null)
                        B[i][j] = Integer.valueOf(binKod.substring(i, i + 1));
                    else B[i][j] = random.nextInt(P);
                }
                if (j - i == 1) {
                    B[i][j] = 1;
                }
            }

        boolean fl = false;

        matrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(P);
                if (matrix[i][j] != 0 && fl == false) fl = true;
            }
        if (fl == false) matrix[0][0] = 1;

    }

    private void action(ActionSaveFile actionSaveFile) {
        if (flag == true)
        t.stop();
        data = drawThread.getJson().toString();
        actionSaveFile.setData(data);
        flag = false;
    }

    public void saveFile(String path, Object saveObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(saveObject);
        FileWriter FW = null;
        try {
            FW = new FileWriter(path, false);
            FW.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (FW != null)
                    FW.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
