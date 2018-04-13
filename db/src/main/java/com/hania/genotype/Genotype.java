package com.hania.genotype;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Genotype {

    private Integer alfa;

    private Integer beta;

    private Integer gamma;

    public Genotype(Integer alfa, Integer beta, Integer gamma) {
        this.alfa = alfa;
        this.beta = beta;
        this.gamma = gamma;
    }

    public Integer getAlfa() {
        return alfa;
    }

    public Integer getBeta() {
        return beta;
    }

    public Integer getGamma() {
        return gamma;
    }
}
