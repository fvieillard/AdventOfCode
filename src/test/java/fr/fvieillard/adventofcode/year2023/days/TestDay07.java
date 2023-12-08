package fr.fvieillard.adventofcode.year2023.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.common.Day;

public class TestDay07 {
    @Test
    void testPart1() {
        Day dayToTest = new Day07(this.getClass().getResourceAsStream("day_07.txt"));
        Assertions.assertEquals(6440L, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day07b(this.getClass().getResourceAsStream("day_07.txt"));
        Assertions.assertEquals(5905L, dayToTest.getSolutionPart2());
    }
}
