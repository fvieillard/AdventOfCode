package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day09 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();

    public Day09(InputStream input) {
        super(9, "Mirage Maintenance", input);
    }

    public static void main(String... args) {
        new Day09(Day09.class.getResourceAsStream("day_09.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return getInput().lines()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::valueOf)
                        .toList())
                .mapToInt(value -> extrapolate(value))
                .sum();
    }

    @Override
    public Object getSolutionPart2() {
        return getInput().lines()
                .map(s -> Arrays.stream(s.split(" "))
                        .map(Integer::valueOf)
                        .toList())
                .mapToInt(value -> extrapolateBefore(value))
                .sum();
    }

    static Integer extrapolate(List<Integer> measures) {
        LOG.debug("Extrapolating for: {}", measures);

        List<Integer> reduction = new ArrayList<>(measures.size() - 1);
        boolean allZeros = true;
        for (int i = 1; i < measures.size(); i++) {
            int elem = measures.get(i) - measures.get(i - 1);
            allZeros = allZeros && elem == 0;
            reduction.add(elem);
        }

        LOG.debug("   {}", reduction);

        return measures.get(measures.size() - 1) + (allZeros ? 0 : extrapolate(reduction));
    }

    static Integer extrapolateBefore(List<Integer> measures) {
        LOG.debug("Extrapolating (before) for: {}", measures);

        List<Integer> reduction = new ArrayList<>(measures.size() - 1);
        boolean allZeros = true;
        for (int i = 1; i < measures.size(); i++) {
            int elem = measures.get(i) - measures.get(i - 1);
            allZeros = allZeros && elem == 0;
            reduction.add(elem);
        }

        LOG.debug("   {}", reduction);

        return measures.get(0) - (allZeros ? 0 : extrapolateBefore(reduction));
    }
}
