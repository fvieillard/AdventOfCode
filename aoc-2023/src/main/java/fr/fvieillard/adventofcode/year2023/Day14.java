package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.fvieillard.adventofcode.common.ArrayUtils;

import static fr.fvieillard.adventofcode.common.ArrayUtils.charArrayToString;
import static fr.fvieillard.adventofcode.common.ArrayUtils.rotateArray90Clockwise;

public class Day14 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();


    public Day14(InputStream input) {
        super(14, "Parabolic Reflector Dish", input);
    }

    public static void main(String... args) {
        new Day14(Day14.class.getResourceAsStream("day_14.txt")).printDay();
    }


    void rollRocksUp(char[][] array) {
        for (int r = 1; r < array.length; r++) {
            for (int c = 0; c < array[0].length; c++) {
                if (array[r][c] == 'O') {
                    int r2 = r;
                    while (r2 > 0 && array[r2 - 1][c] == '.') {
                        r2--;
                        array[r2][c] = 'O';
                        array[r2 + 1][c] = '.';
                    }
                }
            }
        }
    }

    long totalLoad(char[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        long totalWeight = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (array[r][c] == 'O') totalWeight += (rows - r);
            }
        }

        return totalWeight;
    }

    long hash(char[][] array) {
        int rows = array.length;
        int cols = array[0].length;
        long hash = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (array[r][c] == 'O') hash += (r * cols) + c;
            }
        }

        return hash;
    }

    @Override
    public Object getSolutionPart1() {
        char[][] array = ArrayUtils.stringToCharArray(getInput());

        LOG.debug("array before: \n{}", charArrayToString(array));
        rollRocksUp(array);
        LOG.debug("array after: \n{}", charArrayToString(array));


        return totalLoad(array);
    }


    public Object getSolutionPart2() {
        List<Long> hashPerCycle = new ArrayList<>();
        List<Long> loadPerCycle = new ArrayList<>();
        char[][] array = ArrayUtils.stringToCharArray(getInput());

//        LOG.debug("array before: \n{}", charArrayToString(array));
        System.err.println();
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 1000000000; i++) {

            rollRocksUp(array);
            array = rotateArray90Clockwise(array);
            rollRocksUp(array);
            array = rotateArray90Clockwise(array);
            rollRocksUp(array);
            array = rotateArray90Clockwise(array);
            rollRocksUp(array);
            array = rotateArray90Clockwise(array);


            long hash = hash(array);
            LOG.trace("After cycle {}, load is {}, hash is {}", i, totalLoad(array), hash);
            if (hashPerCycle.contains(hash)) {
                int idx = hashPerCycle.indexOf(hash) + 1;
                int resultcycle = (1000000000 - idx) % (i - idx) + idx;
                LOG.debug("""
                                Loop identified between cycle {} and {} (length {}). \
                                That means the result is the same as that after cycle {} ( (1000000000 - {}) % {} + {} ) \
                                """,
                        idx, i, i - idx, resultcycle, idx, i - idx, idx);
                return loadPerCycle.get(resultcycle - 1);
            } else {
                hashPerCycle.add(hash);
                loadPerCycle.add(totalLoad(array));
            }
        }

        return totalLoad(array);
    }
}
