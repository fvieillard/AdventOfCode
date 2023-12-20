package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import fr.fvieillard.adventofcode.Day;

public class TestDay14 {
    @Test
    void testPart1() {
        Day dayToTest = new Day14(this.getClass().getResourceAsStream("day_14.txt"));
        Assertions.assertEquals("136", String.valueOf(dayToTest.getSolutionPart1()));
    }

    @Test
    void testPart2() {
        Day dayToTest = new Day14(this.getClass().getResourceAsStream("day_14.txt"));
        Assertions.assertEquals("64", String.valueOf(dayToTest.getSolutionPart2()));
    }
}
