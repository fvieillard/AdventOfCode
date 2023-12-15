package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day15 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();


    public Day15(InputStream input) {
        super(15, "Lens Library", input);
    }

    public static void main(String... args) {
        new Day15(Day15.class.getResourceAsStream("day_15.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return Arrays.stream(getInput().trim().split(","))
                .mapToInt(Day15::hashAlgorithm)
                .sum();
    }


    public static int hashAlgorithm(String input) {
        int currentValue = 0;
        for (int i = 0; i < input.length(); i++) {
            currentValue += input.charAt(i);
            currentValue = (currentValue * 17) % 256;
        }
        LOG.debug("{} -> {}", input, currentValue);
        return currentValue;
    }

    public Object getSolutionPart2() {
        return null;
    }
}
