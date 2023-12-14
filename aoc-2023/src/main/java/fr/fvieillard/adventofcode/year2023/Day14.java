package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.fvieillard.adventofcode.common.ArrayUtils;

public class Day14 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();


    public Day14(InputStream input) {
        super(14, "Parabolic Reflector Dish", input);
    }

    public static void main(String... args) {
        new Day14(Day14.class.getResourceAsStream("day_14.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        char[][] array = ArrayUtils.stringToCharArray(getInput());

        int rows = array.length;
        int cols = array[0].length;

        LOG.debug("array before: \n{}", ArrayUtils.charArrayToString(array));

        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (array[r][c] == 'O') {
                    int r2 = r ;
                    while (r2 > 0 && array[r2 - 1][c] == '.') {
                        r2--;
                        array[r2][c] = 'O';
                        array[r2 + 1][c] = '.';
                    }
                }
            }
        }
        
        LOG.debug("array after: \n{}", ArrayUtils.charArrayToString(array));

        long totalWeight = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (array[r][c] == 'O') totalWeight += (rows - r);
            }
        }

        return totalWeight;
    }


    public Object getSolutionPart2() {
        return null;
    }
}
