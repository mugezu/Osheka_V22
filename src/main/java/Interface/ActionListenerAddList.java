package Interface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionListenerAddList implements ActionListener {
    JPopupMenu menu;
    JButton button;

    public ActionListenerAddList(JPopupMenu menu,JButton button){
        super();
        this.menu=menu;
        this.button=button;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Dimension d = menu.getPreferredSize();
        d.width = Math.max(d.width, button.getWidth() + button.getWidth());
        menu.setPreferredSize(d);
        menu.show(button, 0,button.getHeight());
    }
}
