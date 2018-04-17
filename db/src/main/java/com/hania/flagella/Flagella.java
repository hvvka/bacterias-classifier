package com.hania.flagella;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class Flagella {

    private Integer id;

    private String alpha;

    private String beta;

    private String number;

    public Flagella(String alpha, String beta, String number) {
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
    }

    public Flagella(Integer id, String alpha, String beta, String number) {
        this.id = id;
        this.alpha = alpha;
        this.beta = beta;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlpha() {
        return alpha;
    }

    public void setAlpha(String alpha) {
        this.alpha = alpha;
    }

    public String getBeta() {
        return beta;
    }

    public void setBeta(String beta) {
        this.beta = beta;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
