package com.hania.view;

import javax.swing.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainFrame extends JFrame {

    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;

    private JPanel mainPanel;
    private JTextField databaseUrlText;
    private JButton testConnectionButton;
    private JTextField genotypeText;
    private JButton classifyButton;
    private JButton addToListButton;
    private JList laterExaminedList;
    private JButton classifyAllButton;
    private JScrollPane examinedScrollPane;

    public MainFrame() {
        super("Bacteria Classifier");
        setSize(WIDTH, HEIGHT);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTextField getDatabaseUrlText() {
        return databaseUrlText;
    }

    public JButton getTestConnectionButton() {
        return testConnectionButton;
    }

    public JTextField getGenotypeText() {
        return genotypeText;
    }

    public JButton getClassifyButton() {
        return classifyButton;
    }

    public JButton getAddToListButton() {
        return addToListButton;
    }

    public JList getLaterExaminedList() {
        return laterExaminedList;
    }

    public JButton getClassifyAllButton() {
        return classifyAllButton;
    }

    public JScrollPane getExaminedScrollPane() {
        return examinedScrollPane;
    }

    public void setExaminedScrollPane(JScrollPane examinedScrollPane) {
        this.examinedScrollPane = examinedScrollPane;
    }
}
