package com.hania;

import com.hania.controller.ErrorMessageUtil;
import com.hania.controller.MainController;
import com.hania.examined.Examined;
import com.hania.knn.NearestNeighbour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Bacterias {

    private static final Logger LOG = LoggerFactory.getLogger(Bacterias.class);

    public static void main(String[] args) {
//        NearestNeighbour nearestNeighbour = new NearestNeighbour("321242");
//        Examined examined = nearestNeighbour.classify();
//        LOG.info("Class of {}: {}", examined.getGenotype(), examined.getClassification());
        new MainController();
//        ErrorMessageUtil.show(new SQLException("test"));
    }
}
