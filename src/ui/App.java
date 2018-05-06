package ui;

import javax.swing.*;
import java.io.IOException;



public class App {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    public static void main(String[] args) throws IOException{
        JFrame mainFrame = new MainFrame("PDF File Merger");

        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
