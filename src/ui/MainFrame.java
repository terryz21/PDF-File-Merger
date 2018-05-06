package ui;

import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame {
    private ContentPanel panel;
    private JList<String> displayList;

    public MainFrame(String title) {
        super(title);

        // set layout manager
        this.setLayout(new BorderLayout());

        // swing components
        DefaultListModel<String> fileList = new DefaultListModel<>();

        this.displayList = new JList<>(fileList);
        this.panel = new ContentPanel(fileList, this.displayList);

        // add to content panel
        Container c = this.getContentPane();
        c.add(this.displayList, BorderLayout.CENTER);
        c.add(this.panel, BorderLayout.WEST);
    }
}
