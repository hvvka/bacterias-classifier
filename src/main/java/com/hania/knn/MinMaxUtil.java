package com.hania.knn;

import com.hania.flagella.Flagella;
import com.hania.toughness.Toughness;

import java.util.List;
import java.util.Optional;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
class MinMaxUtil {

    private MinMaxUtil() {
        throw new IllegalStateException("Utility class!");
    }

    static Integer getMinFlagellaAlpha(List<Flagella> neighbours) {
        Optional<Integer> minFlagellaAlpha = neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlpha()))
                .min(Integer::compare);
        return minFlagellaAlpha.orElse(0);
    }

    static Integer getMaxFlagellaAlpha(List<Flagella> neighbours) {
        Optional<Integer> maxFlagellaAlpha = neighbours.stream()
                .map(f -> Integer.valueOf(f.getAlpha()))
                .max(Integer::compare);
        return maxFlagellaAlpha.orElse(1);
    }

    static Integer getMinFlagellaBeta(List<Flagella> neighbours) {
        Optional<Integer> minFlagellaBeta = neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare);
        return minFlagellaBeta.orElse(0);
    }

    static Integer getMaxFlagellaBeta(List<Flagella> neighbours) {
        Optional<Integer> maxFlagellaBeta = neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare);
        return maxFlagellaBeta.orElse(1);
    }

    static Integer getMinToughnessBeta(List<Toughness> neighbours) {
        Optional<Integer> minToughnessBeta = neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .min(Integer::compare);
        return minToughnessBeta.orElse(0);
    }

    static Integer getMaxToughnessBeta(List<Toughness> neighbours) {
        Optional<Integer> maxToughnessBeta = neighbours.stream()
                .map(f -> Integer.valueOf(f.getBeta()))
                .max(Integer::compare);
        return maxToughnessBeta.orElse(1);
    }

    static Integer getMinToughnessGamma(List<Toughness> neighbours) {
        Optional<Integer> minToughnessGamma = neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .min(Integer::compare);
        return minToughnessGamma.orElse(0);
    }

    static Integer getMaxToughnessGamma(List<Toughness> neighbours) {
        Optional<Integer> maxToughnessGamma = neighbours.stream()
                .map(f -> Integer.valueOf(f.getGamma()))
                .max(Integer::compare);
        return maxToughnessGamma.orElse(1);
    }
}
