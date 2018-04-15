package com.hania.knn;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class NeighbourNotFoundException extends Exception {

    public NeighbourNotFoundException() {
        super("Neighbour not present.");
    }
}
