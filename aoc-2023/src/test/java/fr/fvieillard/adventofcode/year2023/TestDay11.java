package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay11 {
    @Test
    void testPart1() {
        Day dayToTest = new Day11(this.getClass().getResourceAsStream("day_11.txt"));
        Assertions.assertEquals(374L, dayToTest.getSolutionPart1());
    }

    @Test
    void testPart2a() {
        Day11 dayToTest = new Day11(this.getClass().getResourceAsStream("day_11.txt"));
        Assertions.assertEquals(1030L, Day11.sumOfDistances(dayToTest.expandedUniverse(9)));
    }
    @Test
    void testPart2b() {
        Day11 dayToTest = new Day11(this.getClass().getResourceAsStream("day_11.txt"));
        Assertions.assertEquals(8410L, Day11.sumOfDistances(dayToTest.expandedUniverse(99)));
    }
}
