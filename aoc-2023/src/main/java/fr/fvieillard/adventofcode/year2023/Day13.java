package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.fvieillard.adventofcode.common.ArrayUtils;

public class Day13 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();


    Map<String, int[][]> reflections = new HashMap<>();

    public Day13(InputStream input) {
        super(12, "Point of Incidence", input);
    }

    public static void main(String... args) {
        new Day13(Day13.class.getResourceAsStream("day_13.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return Arrays.stream(getInput().split("\n\n"))
                .mapToInt(Day13::summarizeNote)
                .sum();
    }


    public Object getSolutionPart2() {
        return Arrays.stream(getInput().split("\n\n"))
                .mapToInt(Day13::summarizeNote2)
                .sum();
    }


    static int summarizeNote2(String input) {
        LOG.debug("Map: \n{}", input);
        char[][] array = ArrayUtils.stringToCharArray(input);

        LOG.debug("=== Searching horizontal reflection");
        int ref = findReflection2(array);
        if (ref > 0) {
            LOG.debug("Found new horizontal reflection between rows {} and {}", ref - 1, ref);
            return 100 * ref;
        }

        LOG.debug("=== Searching vertical reflection");
            ref = findReflection2(ArrayUtils.rotateArray90Clockwise(array));
        if (ref > 0) {
            LOG.debug("Found new vertical reflection between columns {} and {}", ref - 1, ref);
            return ref;
        }

        LOG.warn("Found no reflection for input :\n{}", input);
        return 0;
    }


    static int summarizeNote(String input) {
        LOG.debug("Map: \n{}", input);
        char[][] array = ArrayUtils.stringToCharArray(input);

        int ref = findReflection(array);
        if (ref > 0) {
            LOG.debug("Found horizontal reflection between rows {} and {}", ref - 1, ref);
            return 100 * ref;
        }

        // rotateArray 90° right and start again to find vertical reflexion
        ref = findReflection(ArrayUtils.rotateArray90Clockwise(array));
        if (ref > 0) {
            LOG.debug("Found vertical reflection between columns {} and {}", ref - 1, ref);
            return ref;
        }

        LOG.warn("Found no reflexion for input: \n{}", input);
        return 0;
    }

    static int findReflection(char[][] input) {
        return findReflection(input, 0);
    }

    static int findReflection(char[][] input, int ignore) {
        for (int i = 0; i < input.length - 1; i++) {
            if (i == ignore - 1) continue;
            LOG.trace("Is reflection between line {} and {} ?", i, i + 1);
            Boolean symetryDetected = null;
            for (int j = 1; j <= Math.min(input.length - i - 1, i + 1); j++) {
                LOG.trace("At line {}, comparing {} and {} ({} - {})", i, i - j + 1, i + j, input[i - j + 1], input[i + j]);
                if (Arrays.equals(input[i - j + 1], input[i + j])) {
                    LOG.trace(" --> TRUE");
                    symetryDetected = true;
                } else {
                    LOG.trace(" --> FALSE, breaking");
                    symetryDetected = false;
                    break;
                }
            }
            if (Boolean.TRUE.equals(symetryDetected)) return i + 1;
        }
        return 0;
    }

    static int findReflection2(char[][] input) {
        int originalReflection = findReflection(input);
        LOG.debug("Original reflexion: {}", originalReflection);
        for (int r = 0; r < input.length; r++) {
            for (int c = 0; c < input[0].length; c++) {
                char current = input[r][c];
                input[r][c] = current == '.' ? '#' : '.';
                int ref = findReflection(input, originalReflection);
                input[r][c] = current;

                LOG.debug("-> Flipping char at {},{} -> new reflection: {}", r, c, ref);
                if (ref > 0 && ref != originalReflection) {
                    return ref;
                }
            }
        }
        return 0;
    }
}
