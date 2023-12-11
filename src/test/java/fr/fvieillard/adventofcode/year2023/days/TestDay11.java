package fr.fvieillard.adventofcode.year2023.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.common.Day;

public class TestDay11 {
    @Test
    void testPart1() {
        Day dayToTest = new Day11(this.getClass().getResourceAsStream("day_11.txt"));
        Assertions.assertEquals(374, dayToTest.getSolutionPart1());
    }

    @Test
    void testPart2() {
        Day dayToTest = new Day11(this.getClass().getResourceAsStream("day_11.txt"));
        Assertions.assertEquals(null, dayToTest.getSolutionPart2());
    }
}
