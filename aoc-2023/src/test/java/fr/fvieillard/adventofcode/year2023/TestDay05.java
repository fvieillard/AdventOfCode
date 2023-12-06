package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay05 {

    @Test
    void testPart1() {
        Day dayToTest = new Day05(this.getClass().getResourceAsStream("day_05.txt"));
        Assertions.assertEquals(35L, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day05(this.getClass().getResourceAsStream("day_05.txt"));
        Assertions.assertEquals(46L, dayToTest.getSolutionPart2());
    }
}
