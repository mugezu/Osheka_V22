package Interface;

import Logic.FillingFile;
import Logic.Matrix;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by Roman on 17.03.2017.
 */
public class SwingMain extends JFrame {
    private static Hashtable<Integer, String> table;
    private static int n;
    private JFileChooser fileChooser = null;
    private final static int P =7;
    private static int[][] matrix;
    private static int[][] A;
    private static int[][] B;
    private static boolean flag = false;
    Thread t;
    Runnable r;

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
        setPreferredSize(new Dimension(700, 700));
        pack();
        setVisible(true);
    }

    private void addComponents(Container contentPane) throws IOException {
        contentPane.setLayout(new BorderLayout());
        JToolBar toolBar = new JToolBar();
        fileChooser = new JFileChooser();
        toolBar.add(createButtonWithMenu());
        contentPane.add(toolBar, BorderLayout.NORTH);
    }

    private JComponent createButtonWithMenu() {
        final JButton fileButton = new JButton("Файл");
        final JPopupMenu menuFile = new JPopupMenu("Файл");
        AbstractAction actionSaveFile = new ActionSaveFile("Сохранить в файл логи программы", fileChooser, SwingMain.this);
        menuFile.add(actionSaveFile);
        actionSaveFile = new ActionSaveFile("Сохранить результат в файл", fileChooser, SwingMain.this);
        menuFile.add(actionSaveFile);
        fileButton.addActionListener(new ActionListenerAddList(menuFile, fileButton));
        final JButton menuButton = new JButton("Опции");
        final JPopupMenu menuFunction = new JPopupMenu("Опции");

        menuFunction.add(new AbstractAction("Старт") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag == false) {
                    if (matrix != null) {
                        r = new DrawThread(matrix,A,B, P, getContentPane());
                        t = new Thread(r);
                        t.start();
                        flag = true;
                    }
                }
            }
        });
        menuFunction.add(new AbstractAction("Стоп") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flag == true) ;
                t.stop();
                flag = false;
            }
        });

        menuFunction.add(new AbstractAction("Ввести степень многочлена") {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        menuButton.addActionListener(new ActionListenerAddList(menuFunction, menuButton));

        final JButton helpButton = new JButton("Справка");
        final JPopupMenu helpFunction = new JPopupMenu("Справка");

        helpFunction.add(new AbstractAction("Об авторе") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "Автор продукта ст. Группи КИТ-64 \nОшека Роман\nВсе права защищены", "Об авторе", JOptionPane.CLOSED_OPTION);
            }
        });
        helpButton.addActionListener(new ActionListenerAddList(helpFunction,helpButton));
        JComponent res = new JComponent() {
        };
        res.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        res.add(fileButton);
        res.add(menuButton);
        res.add(helpButton);
        return res;
    }

    public void GenerateMatrix(int n) {
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

        boolean fl=false;

        matrix = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                matrix[i][j] = random.nextInt(P);
                if (matrix[i][j] != 0 && fl==false) fl=true;
            }
        if (fl==false) matrix[0][0]=1;

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
