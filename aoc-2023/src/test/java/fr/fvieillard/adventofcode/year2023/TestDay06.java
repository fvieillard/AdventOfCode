package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay06 {

    @Test
    void testPart1() {
        Day dayToTest = new Day06(this.getClass().getResourceAsStream("day_06.txt"));
        Assertions.assertEquals(288L, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day06(this.getClass().getResourceAsStream("day_06.txt"));
        Assertions.assertEquals(71503L, dayToTest.getSolutionPart2());
    }
}
