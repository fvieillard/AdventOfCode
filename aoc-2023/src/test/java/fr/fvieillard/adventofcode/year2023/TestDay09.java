package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay09 {
    @Test
    void testPart1() {
        Day dayToTest = new Day09(this.getClass().getResourceAsStream("day_09.txt"));
        Assertions.assertEquals(114, dayToTest.getSolutionPart1());
    }

    @Test
    void testPart2() {
        Day dayToTest = new Day09(this.getClass().getResourceAsStream("day_09.txt"));
        Assertions.assertEquals(2, dayToTest.getSolutionPart2());
    }
}
