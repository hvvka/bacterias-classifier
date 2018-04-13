package com.hania.knn;

import com.hania.SqliteConnection;
import com.hania.SqliteConnectionImpl;
import com.hania.genotype.Genotype;
import com.hania.examined.Examined;
import com.hania.flagella.Flagella;
import com.hania.genotype.GenotypeService;
import com.hania.toughness.Toughness;

import java.sql.Connection;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class NearestNeighbour {

    private String genotype;

    private List<Flagella> flagellaList;

    private List<Toughness> toughnessList;

    public NearestNeighbour(String genotype, List<Flagella> flagellaList, List<Toughness> toughnessList) {
        this.genotype = genotype;
        this.flagellaList = flagellaList;
        this.toughnessList = toughnessList;
    }

    public Examined classify() {
        String classification = "";
        // TODO algorithm
        List<Genotype> neighbours = getNeighbours();
        countDistance();
        return new Examined(this.genotype, classification);
    }

    private List<Genotype> getNeighbours() {
        SqliteConnection sqliteConnection = new SqliteConnectionImpl();
        Connection connection = sqliteConnection.connect();
        GenotypeService genotypeService = new GenotypeService(connection);
        List<Genotype> neighbours = genotypeService.selectAll();
        sqliteConnection.disconnect();
        return neighbours;
    }

    private double countDistance() {
        return Math.sqrt(countAlfa() + countBeta() + countGamma());
    }

    private double countAlfa() {
        return ( (getAlfa() - getMaxAlfa())/(getMaxAlfa() - getMinAlfa()) )^2;
    }

    private Integer getMinAlfa() {
        return null;
    }

    private Integer getMaxAlfa() {
        return null;
    }

    private double countBeta() {
        return ( (getBeta() - getMaxBeta())/(getMaxBeta() - getMinBeta()) )^2;
    }

    private Integer getMinBeta() {
        return null;
    }

    private Integer getMaxBeta() {
        return null;
    }

    private double countGamma() {
        return ( (getGamma() - getMaxGamma())/(getMaxGamma() - getMinGamma()) )^2;
    }

    private Integer getMinGamma() {
        return null;
    }

    private Integer getMaxGamma() {
        return null;
    }

    private Genotype getGenotype() {
        Integer alfa = getAlfa();
        Integer beta = getBeta();
        Integer gamma = getGamma();
        return new Genotype(alfa, beta, gamma);
    }

    private Integer getAlfa() {
        return Integer.valueOf(String.valueOf(genotype.charAt(0)) + String.valueOf(genotype.charAt(5)));
    }

    private Integer getBeta() {
        return Integer.valueOf(String.valueOf(genotype.charAt(1)) + String.valueOf(genotype.charAt(4)));
    }

    private Integer getGamma() {
        return Integer.valueOf(genotype.substring(2, 4));
    }
}
