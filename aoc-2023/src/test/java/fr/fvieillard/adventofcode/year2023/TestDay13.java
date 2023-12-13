package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay13 {
    @Test
    void testPart1() {
        Day dayToTest = new Day13(this.getClass().getResourceAsStream("day_13.txt"));
        Assertions.assertEquals(405, dayToTest.getSolutionPart1());
    }

    @Test
    void testPart2() {
        Day dayToTest = new Day13(this.getClass().getResourceAsStream("day_13.txt"));
        Assertions.assertEquals(400, dayToTest.getSolutionPart2());
    }
}
