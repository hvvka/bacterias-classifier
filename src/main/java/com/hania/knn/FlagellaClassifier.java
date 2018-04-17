package com.hania.knn;

import com.hania.DatabaseConnection;
import com.hania.DatabaseConnectionImpl;
import com.hania.flagella.Flagella;
import com.hania.flagella.FlagellaService;
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
class FlagellaClassifier {

    private static final Logger LOG = LoggerFactory.getLogger(FlagellaClassifier.class);

    private Integer minAlpha;
    private Integer maxAlpha;
    private Integer minBeta;
    private Integer maxBeta;

    private Integer alpha;
    private Integer beta;

    FlagellaClassifier(Integer alpha, Integer beta) {
        this.alpha = alpha;
        this.beta = beta;
    }

    String classify() throws NeighbourNotFoundException {
        List<Flagella> neighbours = getFlagellaNeighbours();
        initMinMax(neighbours);
        Pair<String, String> nearestNeighbour = countNearestNeighbour(neighbours);
        return getNearestFlagella(neighbours, nearestNeighbour);
    }

    private String getNearestFlagella(List<Flagella> neighbours, Pair<String, String> nearestNeighbour)
            throws NeighbourNotFoundException {
        Optional<Flagella> nearestFlagella = neighbours.stream()
                .filter(f -> f.getAlpha().equals(nearestNeighbour.getKey()) && f.getBeta().equals(nearestNeighbour.getValue()))
                .findFirst();
        if (nearestFlagella.isPresent()) {
            return nearestFlagella.get().getNumber();
        } else throw new NeighbourNotFoundException();
    }

    private void initMinMax(List<Flagella> neighbours) {
        minAlpha = MinMaxUtil.getMinFlagellaAlpha(neighbours);
        maxAlpha = MinMaxUtil.getMaxFlagellaAlpha(neighbours);
        minBeta = MinMaxUtil.getMinFlagellaBeta(neighbours);
        maxBeta = MinMaxUtil.getMaxFlagellaBeta(neighbours);
    }

    private List<Flagella> getFlagellaNeighbours() {
        try (DatabaseConnection databaseConnection = new DatabaseConnectionImpl()) {
            Connection connection = databaseConnection.connect();
            FlagellaService flagellaService = new FlagellaService(connection);
            return flagellaService.selectAll();
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return Collections.emptyList();
    }

    private Pair<String, String> countNearestNeighbour(List<Flagella> neighbours) {
        Double distance;
        Double currentMin = Double.MAX_VALUE;
        String nearestAlpha = null;
        String nearestBeta = null;
        for (Flagella neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestAlpha = neighbour.getAlpha();
                nearestBeta = neighbour.getBeta();
                currentMin = distance;
            }
        }
        return new Pair<>(nearestAlpha, nearestBeta);
    }

    private double countFlagellaDistance(Flagella flagella) {
        Integer alphaNeighbour = Integer.valueOf(flagella.getAlpha());
        Integer betaNeighbour = Integer.valueOf(flagella.getBeta());
        return Math.sqrt(countAlpha(alphaNeighbour) + countBeta(betaNeighbour));
    }

    // Normalized Euclidean Distance
    private double countAlpha(Integer alphaNeighbour) {
        return Math.pow((double) (alpha - alphaNeighbour) / (maxAlpha - minAlpha), 2);
    }

    private double countBeta(Integer betaNeighbour) {
        return Math.pow((double) (beta - betaNeighbour) / (maxBeta - minBeta), 2);
    }
}
