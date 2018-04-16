package com.hania.controller;

import com.hania.DatabaseConnection;
import com.hania.DatabaseConnectionImpl;
import com.hania.examined.*;
import com.hania.knn.NearestNeighbour;
import com.hania.knn.NeighbourNotFoundException;
import com.hania.view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MainController {

    private MainFrame mainFrame;

    private JTextField databaseUrlText;
    private JButton testConnectionButton;
    private JTextField genotypeText;
    private JButton classifyButton;
    private JButton addToListButton;
    private JList laterExaminedList;
    private DefaultListModel<String> listModel;
    private JButton classifyAllButton;
    private JScrollPane examinedScrollPane;
    private JTable examinedTable;
    private DefaultTableModel tableModel;

    private DatabaseConnection databaseConnection;
    private String databaseUrl;
    private ExaminedService examinedService;

    public MainController() {
        databaseConnection = new DatabaseConnectionImpl();
        mainFrame = new MainFrame();
        mainFrame.addWindowListener(getWindowAdapter());
        databaseUrl = "";
        initComponents();
        initListeners();
    }

    private WindowAdapter getWindowAdapter() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "Do you want to quit?");
                if (option == 0) {
                    closeConnection();
                    System.exit(0);
                }
            }
        };
    }

    private void closeConnection() {
        if (databaseConnection.getConnection() != null) {
            try {
                databaseConnection.close();
            } catch (SQLException e) {
                ErrorMessageUtil.show(e);
            }
        }
    }

    private void initComponents() {
        databaseUrlText = mainFrame.getDatabaseUrlText();
        testConnectionButton = mainFrame.getTestConnectionButton();
        examinedScrollPane = mainFrame.getExaminedScrollPane();
        createExaminedTable();
        genotypeText = mainFrame.getGenotypeText();
        classifyButton = mainFrame.getClassifyButton();
        addToListButton = mainFrame.getAddToListButton();
        listModel = new DefaultListModel<>();
        laterExaminedList = mainFrame.getLaterExaminedList();
        laterExaminedList.setModel(listModel);
        classifyAllButton = mainFrame.getClassifyAllButton();
    }

    private void createExaminedTable() {
        String[] columnNames = {"Genotype", "Class"};
        tableModel = new DefaultTableModel(columnNames, 0);
        examinedTable = new JTable(tableModel);
        examinedScrollPane.setViewportView(examinedTable);
    }

    private void initListeners() {
        addTestConnectionListener();
        addClassifyListener();
        addToListListener();
        addClassifyAllListener();
        deleteKeyListener();
    }

    private void deleteKeyListener() {
        laterExaminedList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    listModel.remove(laterExaminedList.getSelectedIndex());
                    laterExaminedList.revalidate();
                }
            }
        });
    }

    private void addClassifyAllListener() {
        classifyAllButton.addActionListener(e -> {
            addAllRecords();
            listModel.clear();
        });
    }

    private void addAllRecords() {
        if (databaseConnection.getConnection() != null) {
            NearestNeighbour nearestNeighbour;
            List<Examined> examinedList = new ArrayList<>();
            for (int i = 0; i < listModel.size(); i++) {
                nearestNeighbour = new NearestNeighbour(listModel.get(i));
                classifyBacteria(nearestNeighbour, examinedList);
            }
            insertExamined(examinedList);
        }
    }

    private void classifyBacteria(NearestNeighbour nearestNeighbour, List<Examined> examinedList) {
        try {
            examinedList.add(nearestNeighbour.classify());
        } catch (NeighbourNotFoundException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private Examined classifyBacteria(NearestNeighbour nearestNeighbour) {
        Examined examined = new Examined();
        try {
            examined = nearestNeighbour.classify();
        } catch (NeighbourNotFoundException e) {
            ErrorMessageUtil.show(e);
        }
        return examined;
    }

    private void insertExamined(List<Examined> examinedList) {
        examinedService.add(examinedList);
        commitChanges();
        updateTable();
    }

    private void insertExamined(Examined examined) {
        examinedService.add(examined);
        commitChanges();
        updateTable();
    }

    private void addToListListener() {
        addToListButton.addActionListener(e -> {
            String genotype = genotypeText.getText();
            if (!"".equals(genotype)) {
                listModel.addElement(genotype);
            }
        });
    }

    private void addClassifyListener() {
        classifyButton.addActionListener(e -> {
            String genotype = genotypeText.getText();
            if (!"".equals(genotype)) {
                addRecord(genotype);
            }
        });
    }

    private void addRecord(String genotype) {
        if (databaseConnection.getConnection() != null) {
            NearestNeighbour nearestNeighbour = new NearestNeighbour(genotype);
            Examined examined = classifyBacteria(nearestNeighbour);
            insertExamined(examined);
        }
    }

    private void commitChanges() {
        try {
            databaseConnection.getConnection().commit();
        } catch (SQLException e) {
            ErrorMessageUtil.show(e);
        }
    }

    private void addTestConnectionListener() {
        testConnectionButton.addActionListener(e -> {
            databaseUrl = databaseUrlText.getText();
            connectDatabase();
        });
    }

    private void connectDatabase() {
        closeConnection();
        if (!"".equals(databaseUrl)) {
            testCustomDatabaseConnection(databaseUrl);
        } else {
            testDefaultDatabaseConnection();
        }
    }

    private void testCustomDatabaseConnection(String databaseUrl) {
        databaseConnection = new DatabaseConnectionImpl(databaseUrl);
        testConnection(databaseConnection);
    }

    private void testDefaultDatabaseConnection() {
        databaseConnection = new DatabaseConnectionImpl();
        testConnection(databaseConnection);
    }

    private void testConnection(DatabaseConnection databaseConnection) {
        if (databaseConnection.connect() != null) {
            examinedService = new ExaminedService(databaseConnection.getConnection());
            databaseUrlText.setBackground(Color.GREEN);
            updateTable();
        } else databaseUrlText.setBackground(Color.RED);
    }

    private void updateTable() {
        List<Examined> examinedList = examinedService.selectAll();
        clearTable();
        for (Examined examined : examinedList) {
            tableModel.addRow(new String[]{examined.getGenotype(), examined.getClassification()});
        }
        tableModel.fireTableDataChanged();
        serializeInBackground(examinedList);
    }

    private void clearTable() {
        if (tableModel.getRowCount() > 0) {
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                tableModel.removeRow(i);
            }
        }
    }

    private void serializeInBackground(List<Examined> examinedList) {
        Runnable serializeExaminedList = () -> {
            XMLExaminedSerializer xmlExaminedSerializer = new XMLExaminedSerializer();
            xmlExaminedSerializer.save(new File("src/main/resources/examined.xml"), new ExaminedList(examinedList));
        };
        new Thread(serializeExaminedList).start();
    }
}
