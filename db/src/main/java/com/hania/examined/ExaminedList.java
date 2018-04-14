package com.hania.examined;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
@XmlRootElement(name="examinedList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExaminedList {

    @XmlElement(name="examined", type=Examined.class)
    private List<Examined> examinedList = new ArrayList<Examined>();

    public ExaminedList() {
    }

    public ExaminedList(List<Examined> examinedList) {
        this.examinedList = examinedList;
    }

    public List<Examined> getExaminedList() {
        return examinedList;
    }

    public void setExaminedList(List<Examined> examinedList) {
        this.examinedList = examinedList;
    }
}
