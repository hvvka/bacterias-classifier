package com.hania.controller;

import com.hania.knn.NeighbourNotFoundException;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class ErrorMessageUtil {

    private ErrorMessageUtil() {
        throw new IllegalStateException("Utility class!");
    }

    public static void show(SQLException e) {
        String messageWrapped = "<html><body><p style='width: 400px;'>" + e.getMessage() + "</p></body></html>";
        String title = "SQLException error!";
        JOptionPane.showMessageDialog(new Frame(), messageWrapped, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void show(NeighbourNotFoundException e) {
        String messageWrapped = "<html><body><p style='width: 400px;'>" + e.getMessage() + "</p></body></html>";
        String title = "NeighbourNotFoundException error!";
        JOptionPane.showMessageDialog(new Frame(), messageWrapped, title, JOptionPane.ERROR_MESSAGE);
    }
}
