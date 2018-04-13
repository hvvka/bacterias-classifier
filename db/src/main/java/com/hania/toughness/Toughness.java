package com.hania.toughness;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Toughness {

    private Integer id;

    private String beta;

    private String gamma;

    private String rank;

    public Toughness(String beta, String gamma, String rank) {
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }

    public Toughness(Integer id, String beta, String gamma, String rank) {
        this.id = id;
        this.beta = beta;
        this.gamma = gamma;
        this.rank = rank;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getGamma() {
        return gamma;
    }

    public void setGamma(String gamma) {
        this.gamma = gamma;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
