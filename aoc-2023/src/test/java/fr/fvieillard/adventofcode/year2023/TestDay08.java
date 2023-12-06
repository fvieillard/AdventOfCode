package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay08 {
    @Test
    void testPart1() {
        Day dayToTest = new Day08(this.getClass().getResourceAsStream("day_08.txt"));
        Assertions.assertEquals(2, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day08(this.getClass().getResourceAsStream("day_08b.txt"));
        Assertions.assertEquals(6, dayToTest.getSolutionPart2());
    }
}
