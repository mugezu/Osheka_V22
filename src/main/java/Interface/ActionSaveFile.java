package Interface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by Roman on 17.03.2017.
 */
public class ActionSaveFile extends AbstractAction {
    private JFileChooser fileChooser;
    private JFrame frame;

    public ActionSaveFile(String name, JFileChooser fileChooser, JFrame frame) {
        super(name);
        this.fileChooser = fileChooser;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent e) {
        fileChooser.setDialogTitle("Сохранение файла");
        // Определение режима - только файл
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(frame);
        // Если файл выбран, то представим его в сообщении
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath=fileChooser.getSelectedFile()+".txt";
            JOptionPane.showMessageDialog(frame,
                    "Файл '" + filePath +
                            " ) сохранен");
        }
    }
}
