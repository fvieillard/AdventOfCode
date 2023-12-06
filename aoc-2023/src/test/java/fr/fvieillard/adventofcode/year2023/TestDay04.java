package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay04 {

    @Test
    void testPart1() {
        Day dayToTest = new Day04(this.getClass().getResourceAsStream("day_04.txt"));
        Assertions.assertEquals(13, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day04(this.getClass().getResourceAsStream("day_04.txt"));
        Assertions.assertEquals(30, dayToTest.getSolutionPart2());
    }
}
