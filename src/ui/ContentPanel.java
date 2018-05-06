package ui;

import model.FileQueue;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ContentPanel extends JPanel {
    public final static int WIDTH = 200;
    public final static int HEIGHT = 400;

    private JButton addButton;
    private JButton removeButton;
    private JButton clearAllButton;
    private JButton mergeButton;

    private JFileChooser fileChooser;
    private List<File> queueFiles = new ArrayList<>(); // list of files to be merged in queue

    /**
     * Class constructor
     *
     * @param list is the default list model list used
     *        to construct displayList (JList) with names of all added files to be merged
     * @param displayList is the JList with names of all added files in merge queue
     */
    public ContentPanel(DefaultListModel list, JList displayList) {

        initializeLayout();

        GridBagConstraints c = new GridBagConstraints();

        // place buttons onto content panel
        c.insets = new Insets(2, 2, 2, 2);
        placeButtons(c);

        // button click handlers
        this.addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addButtonAction(e, list);
            }
        });

        this.mergeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mergeButtonAction(e, list);
            }
        });

        this.removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeButtonAction(e, list, displayList);

            }
        });

        this.clearAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllButtonAction(e, list);
            }
        });
    }

    /**
     * Helper function in constructor that initializes layout
     * of content panel
     */
    private void initializeLayout() {
        Dimension d = this.getPreferredSize();
        d.width = WIDTH;
        d.height = HEIGHT;
        this.setPreferredSize(d);

        // create layout
        this.setLayout(new GridBagLayout());
    }

    /**
     * Helper function in constructor that places buttons
     *
     * @param c is the current GridBagConstraints
     */
    private void placeButtons(GridBagConstraints c) {

        // Add button
        this.addButton = new JButton("Add File");
        this.addButton.setPreferredSize(new Dimension(80, 40));
        c.insets = new Insets(40, 0, 0, 0);
        c.weightx = 0;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        this.add(this.addButton, c);

        // Remove button
        this.removeButton = new JButton("Remove");
        this.removeButton.setPreferredSize(new Dimension(80, 40));
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        this.add(this.removeButton, c);

        // Clear All button
        this.clearAllButton = new JButton("Clear All");
        this.clearAllButton.setPreferredSize(new Dimension(80,40));
        c.insets = new Insets(0,0,0,0);
        c.weightx = 0;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        this.add(this.clearAllButton, c);

        // Merge button
        this.mergeButton = new JButton("Merge");
        this.mergeButton.setPreferredSize(new Dimension(80,40));
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(30,0,0,0);
        c.weightx = 0;
        c.weighty = 5;
        c.gridx = 0;
        c.gridy = 3;
        this.add(this.mergeButton, c);
    }

    /**
     *
     * Helper for add button handler which allows user
     * to select file to be merged and adds it to list
     */
    private void addButtonAction(ActionEvent e, DefaultListModel list) {
        fileChooser = new JFileChooser();
        if (e.getSource() == addButton) {
            int returnVal = fileChooser.showOpenDialog(this.addButton);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                list.addElement(file.getName());
                this.queueFiles.add(file);
            }
        }
    }

    /**
     *
     * Helper for merge button handler which allows user
     * to merge selected pdf files into one doc
     */
    private void mergeButtonAction(ActionEvent e, DefaultListModel list) {
        try {
            doMerge(e);
            list.removeAllElements();

        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "Error: All uploaded documents must be in " +
                    "PDF format");
            e1.printStackTrace();
        }
    }

    /**
     *
     * Helper for above mergeButtonAction
     *
     * @throws IOException
     */
    private void doMerge(ActionEvent e) throws IOException {
        List<PDDocument> docList = new ArrayList<>(); // list of docs to close after merge
        FileQueue currentQueue = new FileQueue(); // new model.FileQueue object

        for (File file : queueFiles) {
            if (file.exists()) {
                PDDocument doc = PDDocument.load(file);
                docList.add(doc);
                currentQueue.addFile(file);
            }
        }

        // create PDFMergerUtility class
        PDFMergerUtility merge = new PDFMergerUtility();

        // set destination of merged pdf file
        String home = System.getProperty("user.home");
        merge.setDestinationFileName(home + "/Desktop" + "/merged.pdf");

        // load files to be merged into model.FileQueue
        for (File currFile : currentQueue.getAllFiles()) {
            merge.addSource(currFile);
        }

        // merge documents in model.FileQueue
        merge.mergeDocuments();

        // close all documents
        for (PDDocument currDoc : docList) {
            currDoc.close();
        }
    }

    /**
     *
     * Helper function for Clear All button handler
     */
    private void clearAllButtonAction(ActionEvent e, DefaultListModel list) {
        list.clear();
    }

    /**
     *
     * Helper function for Remove button handler
     */
    private void removeButtonAction(ActionEvent e, DefaultListModel list, JList jlist) {
        int index = jlist.getSelectedIndex();
        list.remove(index);

    }


}
