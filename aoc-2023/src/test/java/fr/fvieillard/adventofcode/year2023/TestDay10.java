package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay10 {
    @Test
    void testPart1() {
        Day dayToTest = new Day10(this.getClass().getResourceAsStream("day_10.txt"));
        Assertions.assertEquals(4L, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart1b() {
        Day dayToTest = new Day10(this.getClass().getResourceAsStream("day_10b.txt"));
        Assertions.assertEquals(8L, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day10(this.getClass().getResourceAsStream("day_10.txt"));
        Assertions.assertEquals(null, dayToTest.getSolutionPart2());
    }
}
