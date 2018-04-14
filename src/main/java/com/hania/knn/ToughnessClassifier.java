package com.hania.knn;

import com.hania.SqliteConnection;
import com.hania.SqliteConnectionImpl;
import com.hania.toughness.Toughness;
import com.hania.toughness.ToughnessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class ToughnessClassifier {

    private static final Logger LOG = LoggerFactory.getLogger(ToughnessClassifier.class);

    private Integer minBeta;
    private Integer maxBeta;
    private Integer minGamma;
    private Integer maxGamma;

    private Integer beta;
    private Integer gamma;

    ToughnessClassifier(Integer beta, Integer gamma) {
        this.beta = beta;
        this.gamma = gamma;
    }

    String classify() {
        List<Toughness> neighbours = getToughnessNeighbours();
        initMinMax(neighbours);
        String nearestBeta = countNearestBeta(neighbours);
        String nearestGamma = countNearestGamma(neighbours);
        return neighbours.stream()
                .filter(f -> f.getBeta().equals(nearestBeta) && f.getGamma().equals(nearestGamma))
                .findFirst().get()
                .getRank();
    }

    private void initMinMax(List<Toughness> neighbours) {
        minBeta = getMinBeta(neighbours);
        maxBeta = getMaxBeta(neighbours);
        minGamma = getMinGamma(neighbours);
        maxGamma = getMaxGamma(neighbours);
    }

    private List<Toughness> getToughnessNeighbours() {
        try (SqliteConnection sqliteConnection = new SqliteConnectionImpl()) {
            Connection connection = sqliteConnection.connect();
            ToughnessService toughnessService = new ToughnessService(connection);
            return toughnessService.selectAll();
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }

    private Integer getMinBeta(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare)
                .get();
    }

    private Integer getMaxBeta(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare)
                .get();
    }

    private Integer getMinGamma(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .min(Integer::compare)
                .get();
    }

    private Integer getMaxGamma(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .max(Integer::compare)
                .get();
    }

    private String countNearestBeta(List<Toughness> neighbours) {
        Double distance;
        Double currentMin = 999.9;
        String nearestBeta = null;
        for (Toughness neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestBeta = neighbour.getBeta();
            }
        }
        return nearestBeta;
    }

    private String countNearestGamma(List<Toughness> neighbours) {
        Double distance;
        Double currentMin = 999.9;
        String nearestGamma = null;
        for (Toughness neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestGamma = neighbour.getGamma();
            }
        }
        return nearestGamma;
    }

    private double countFlagellaDistance(Toughness toughness) {
        Integer betaNeighbour = Integer.valueOf(toughness.getBeta());
        Integer gammaNeighbour = Integer.valueOf(toughness.getGamma());
        return Math.sqrt(countBeta(betaNeighbour) + countGamma(gammaNeighbour));
    }

    // Normalized Euclidean Distance
    private double countBeta(Integer betaNeighbour) {
        return Math.pow((double) (beta - betaNeighbour) / (maxBeta - minBeta), 2);
    }

    private double countGamma(Integer gammaNeighbour) {
        return Math.pow((double) (gamma - gammaNeighbour) / (maxGamma - minGamma), 2);
    }
}
