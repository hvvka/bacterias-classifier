package com.hania.knn;

import com.hania.examined.Examined;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class NearestNeighbour {

    private String genotype;

    private FlagellaClassifier flagellaClassifier;

    private ToughnessClassifier toughnessClassifier;

    public NearestNeighbour(String genotype) {
        this.genotype = genotype;
        flagellaClassifier = new FlagellaClassifier(getAlpha(), getBeta());
        toughnessClassifier = new ToughnessClassifier(getBeta(), getGamma());
    }

    public Examined classify() throws NeighbourNotFoundException {
        String flagella = flagellaClassifier.classify();
        String toughness = toughnessClassifier.classify();
        String classification = flagella + toughness;
        return new Examined(this.genotype, classification);
    }

    private Integer getAlpha() {
        return Integer.valueOf(String.valueOf(genotype.charAt(0)) + String.valueOf(genotype.charAt(5)));
    }

    private Integer getBeta() {
        return Integer.valueOf(String.valueOf(genotype.charAt(1)) + String.valueOf(genotype.charAt(4)));
    }

    private Integer getGamma() {
        return Integer.valueOf(genotype.substring(2, 4));
    }
}
