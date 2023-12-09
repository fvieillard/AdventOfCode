package fr.fvieillard.adventofcode.year2023.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.common.Day;

public class TestDay03 {

    @Test
    void testPart1() {
        Day dayToTest = new Day03(this.getClass().getResourceAsStream("day_03.txt"));
        Assertions.assertEquals(4361, dayToTest.getSolutionPart1());
    }
    @Test
    void testPart2() {
        Day dayToTest = new Day03(this.getClass().getResourceAsStream("day_03.txt"));
        Assertions.assertEquals(467835L, dayToTest.getSolutionPart2());
    }
}
