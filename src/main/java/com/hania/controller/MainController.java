package com.hania.controller;

import com.hania.SqliteConnection;
import com.hania.SqliteConnectionImpl;
import com.hania.examined.*;
import com.hania.knn.NearestNeighbour;
import com.hania.view.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
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
    private JButton classifyAllButton;
    private JScrollPane examinedScrollPane;
    private JTable examinedTable;
    private DefaultTableModel tableModel;

    private String databaseUrl;
    private Connection connection;
    private ExaminedService examinedService;

    public MainController() {
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
        try {
            connection.close();
        } catch (SQLException e1) {
            ErrorMessageUtil.show(e1);
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
        laterExaminedList = mainFrame.getLaterExaminedList();
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
        classifyButton.addActionListener(e -> {
            String genotype = genotypeText.getText();
            if (!"".equals(genotype)) {
                addRecord(genotype);
            }
        });
    }

    private void addRecord(String genotype) {
        if (connection != null) {
            closeConnection();
            NearestNeighbour nearestNeighbour = new NearestNeighbour(genotype);
            Examined examined = nearestNeighbour.classify();
            connectDatabase();
            examinedService.add(examined);
//            commitChanges();
            updateTable();
        }
    }

    private void commitChanges() {
        try {
            connection.commit();
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
        if (!"".equals(databaseUrl)) {
            testCustomDatabaseConnection(databaseUrl);
        } else {
            testDefaultDatabaseConnection();
        }
    }

    private void testCustomDatabaseConnection(String databaseUrl) {
        SqliteConnection sqliteConnection = new SqliteConnectionImpl(databaseUrl);
        testConnection(sqliteConnection);
    }

    private void testDefaultDatabaseConnection() {
        SqliteConnection sqliteConnection = new SqliteConnectionImpl();
        testConnection(sqliteConnection);
    }

    private void testConnection(SqliteConnection sqliteConnection) {
        connection = sqliteConnection.connect();
        if (connection != null) {
            examinedService = new ExaminedService(connection);
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
