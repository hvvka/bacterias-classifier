package com.hania.knn;

import com.hania.flagella.Flagella;
import com.hania.toughness.Toughness;

import java.util.List;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class MinMaxUtil {

    private MinMaxUtil() {
        throw new IllegalStateException("Utility class!");
    }

    static Integer getMinFlagellaAfla(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlfa()))
                .min(Integer::compare)
                .get();
    }

    static Integer getMaxFlagellaAfla(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlfa()))
                .max(Integer::compare)
                .get();
    }

    static Integer getMinFlagellaBeta(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare)
                .get();
    }

    static Integer getMaxFlagellaBeta(List<Flagella> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare)
                .get();
    }

    static Integer getMinToughnessBeta(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare)
                .get();
    }

    static Integer getMaxToughnessBeta(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare)
                .get();
    }

    static Integer getMinToughnessGamma(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .min(Integer::compare)
                .get();
    }

    static Integer getMaxToughnessGamma(List<Toughness> neighbours) {
        return neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .max(Integer::compare)
                .get();
    }
}
