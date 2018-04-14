package com.hania.knn;

import com.hania.SqliteConnection;
import com.hania.SqliteConnectionImpl;
import com.hania.flagella.Flagella;
import com.hania.flagella.FlagellaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class FlagellaClassifier {

    private static final Logger LOG = LoggerFactory.getLogger(FlagellaClassifier.class);

    private Integer minAfla;
    private Integer maxAfla;
    private Integer minBeta;
    private Integer maxBeta;

    private Integer alfa;
    private Integer beta;

    FlagellaClassifier(Integer alfa, Integer beta) {
        this.alfa = alfa;
        this.beta = beta;
    }

    String classify() {
        List<Flagella> neighbours = getFlagellaNeighbours();
        initMinMax(neighbours);
        String nearestAlfa = countNearestAlfa(neighbours);
        String nearestBeta = countNearestBeta(neighbours);
        return neighbours.stream()
                .filter(f -> f.getAlfa().equals(nearestAlfa) && f.getBeta().equals(nearestBeta))
                .findFirst().get()
                .getNumber();
    }

    private void initMinMax(List<Flagella> neighbours) {
        minAfla = getMinAfla(neighbours);
        maxAfla = getMaxAfla(neighbours);
        minBeta = getMinBeta(neighbours);
        maxBeta = getMaxBeta(neighbours);
    }

    private List<Flagella> getFlagellaNeighbours() {
        try (SqliteConnection sqliteConnection = new SqliteConnectionImpl()) {
            Connection connection = sqliteConnection.connect();
            FlagellaService flagellaService = new FlagellaService(connection);
            return flagellaService.selectAll();
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }

    private Integer getMinAfla(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlfa()))
                .min(Integer::compare)
                .get();
    }

    private Integer getMaxAfla(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlfa()))
                .max(Integer::compare)
                .get();
    }

    private Integer getMinBeta(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare)
                .get();
    }

    private Integer getMaxBeta(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare)
                .get();
    }

    private String countNearestAlfa(List<Flagella> neighbours) {
        Double distance;
        Double currentMin = 999.9;
        String nearestAlfa = null;
        for (Flagella neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestAlfa = neighbour.getAlfa();
            }
        }
        return nearestAlfa;
    }

    private String countNearestBeta(List<Flagella> neighbours) {
        Double distance;
        Double currentMin = 999.9;
        String nearestBeta = null;
        for (Flagella neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestBeta = neighbour.getBeta();
            }
        }
        return nearestBeta;
    }

    private double countFlagellaDistance(Flagella flagella) {
        Integer alfaNeighbour = Integer.valueOf(flagella.getAlfa());
        Integer betaNeighbour = Integer.valueOf(flagella.getBeta());
        return Math.sqrt(countAlfa(alfaNeighbour) + countBeta(betaNeighbour));
    }

    // Normalized Euclidean Distance
    private double countAlfa(Integer alfaNeighbour) {
        return Math.pow((double) (alfa - alfaNeighbour) / (maxAfla - minAfla), 2);
    }

    private double countBeta(Integer betaNeighbour) {
        return Math.pow((double) (beta - betaNeighbour) / (maxBeta - minBeta), 2);
    }
}
