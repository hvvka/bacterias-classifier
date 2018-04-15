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

    String classify() throws NeighbourNotFoundException {
        List<Flagella> neighbours = getFlagellaNeighbours();
        initMinMax(neighbours);
        Pair<String, String> nearestNeighbour = countNearestNeighbour(neighbours);
        return getNearestFlagella(neighbours, nearestNeighbour);
    }

    private String getNearestFlagella(List<Flagella> neighbours, Pair<String, String> nearestNeighbour)
            throws NeighbourNotFoundException {
        Optional<Flagella> nearestFlagella = neighbours.stream()
                .filter(f -> f.getAlfa().equals(nearestNeighbour.getKey()) && f.getBeta().equals(nearestNeighbour.getValue()))
                .findFirst();
        if (nearestFlagella.isPresent()) {
            return nearestFlagella.get().getNumber();
        } else throw new NeighbourNotFoundException();
    }

    private void initMinMax(List<Flagella> neighbours) {
        minAfla = MinMaxUtil.getMinFlagellaAfla(neighbours);
        maxAfla = MinMaxUtil.getMaxFlagellaAfla(neighbours);
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
        String nearestAlfa = null;
        String nearestBeta = null;
        for (Flagella neighbour : neighbours) {
            distance = countFlagellaDistance(neighbour);
            if (distance < currentMin) {
                nearestAlfa = neighbour.getAlfa();
                nearestBeta = neighbour.getBeta();
                currentMin = distance;
            }
        }
        return new Pair<>(nearestAlfa, nearestBeta);
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
