package com.hania.examined;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@XmlRootElement(name="examined")
@XmlAccessorType(XmlAccessType.FIELD)
public class Examined {

    @XmlAttribute
    private Integer id;

    @XmlElement
    private String genotype;

    @XmlElement
    private String classification;

    public Examined() {
    }

    public Examined(String genotype, String classification) {
        this.genotype = genotype;
        this.classification = classification;
    }

    public Examined(Integer id, String genotype, String classification) {
        this.id = id;
        this.genotype = genotype;
        this.classification = classification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
