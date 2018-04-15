package com.hania.knn;

import com.hania.DatabaseConnection;
import com.hania.DatabaseConnectionImpl;
import com.hania.toughness.Toughness;
import com.hania.toughness.ToughnessService;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    String classify() throws NeighbourNotFoundException {
        List<Toughness> neighbours = getToughnessNeighbours();
        initMinMax(neighbours);
        Pair<String, String> nearestNeighbour = countNearestNeighbour(neighbours);
        return getNearestToughness(neighbours, nearestNeighbour);
    }

    private String getNearestToughness(List<Toughness> neighbours, Pair<String, String> nearestNeighbour)
            throws NeighbourNotFoundException {
        Optional<Toughness> nearestToughness = neighbours.stream()
                .filter(f -> f.getBeta().equals(nearestNeighbour.getKey()) && f.getGamma().equals(nearestNeighbour.getValue()))
                .findFirst();
        if (nearestToughness.isPresent()) {
            return nearestToughness.get().getRank();
        } else throw new NeighbourNotFoundException();
    }

    private void initMinMax(List<Toughness> neighbours) {
        minBeta = MinMaxUtil.getMinToughnessBeta(neighbours);
        maxBeta = MinMaxUtil.getMaxToughnessBeta(neighbours);
        minGamma = MinMaxUtil.getMinToughnessGamma(neighbours);
        maxGamma = MinMaxUtil.getMaxToughnessGamma(neighbours);
    }

    private List<Toughness> getToughnessNeighbours() {
        try (DatabaseConnection databaseConnection = new DatabaseConnectionImpl()) {
            Connection connection = databaseConnection.connect();
            ToughnessService toughnessService = new ToughnessService(connection);
            return toughnessService.selectAll();
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }

    private Pair<String, String> countNearestNeighbour(List<Toughness> neighbours) {
        Double distance;
        Double currentMin = Double.MAX_VALUE;
        String nearestBeta = null;
        String nearestGamma = null;
        for (Toughness neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestBeta = neighbour.getBeta();
                nearestGamma = neighbour.getGamma();
                currentMin = distance;
            }
        }
        return new Pair<>(nearestBeta, nearestGamma);
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
