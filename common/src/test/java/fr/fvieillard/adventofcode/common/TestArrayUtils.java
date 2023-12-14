package fr.fvieillard.adventofcode.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArrayUtils {

    @Test
    void testStringToArray() {
        String input = """
                ABCD
                EFGH
                IJKL""";
        char[][] expected = new char[][]{
                {'A', 'B', 'C', 'D'},
                {'E', 'F', 'G', 'H'},
                {'I', 'J', 'K', 'L'}
        };
        Assertions.assertArrayEquals(expected, ArrayUtils.stringToCharArray(input));
    }

    @Test
    void testArrayToString() {
        String expected = """
                ABCD
                EFGH
                IJKL""";
        char[][] input = new char[][]{
                {'A', 'B', 'C', 'D'},
                {'E', 'F', 'G', 'H'},
                {'I', 'J', 'K', 'L'}
        };
        Assertions.assertEquals(expected, ArrayUtils.charArrayToString(input));
    }

    @Test
    void testRotate90Clockwise() {
        char[][] input = new char[][]{
                {'A', 'B', 'C', 'D'},
                {'E', 'F', 'G', 'H'},
                {'I', 'J', 'K', 'L'}
        };
        char[][] expected = new char[][]{
                {'I', 'E', 'A'},
                {'J', 'F', 'B'},
                {'K', 'G', 'C'},
                {'L', 'H', 'D'}
        };
        Assertions.assertArrayEquals(expected, ArrayUtils.rotateArray90Clockwise(input));
    }
}
