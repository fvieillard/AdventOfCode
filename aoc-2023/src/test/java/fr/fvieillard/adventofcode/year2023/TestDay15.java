package fr.fvieillard.adventofcode.year2023;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import fr.fvieillard.adventofcode.Day;

public class TestDay15 {
    @Test
    void testPart1() {
        Day dayToTest = new Day15(this.getClass().getResourceAsStream("day_15.txt"));
        Assertions.assertEquals("1320", String.valueOf(dayToTest.getSolutionPart1()));
    }

    @ParameterizedTest
    @CsvSource({
            "HASH,52",
            "rn=1,30",
            "cm-,253",
            "qp=3,97",
            "cm=2,47",
            "qp-,14",
            "pc=4,180",
            "ot=9,9",
            "ab=5,197",
            "pc-,48",
            "pc=6,214",
            "ot=7,231"})
    void testHashAlgorithm(String input, int expected) {
        Assertions.assertEquals(expected, Day15.hashAlgorithm(input));
    }

    @Test
    void testPart2() {
        Day dayToTest = new Day15(this.getClass().getResourceAsStream("day_15.txt"));
        Assertions.assertEquals("null", String.valueOf(dayToTest.getSolutionPart2()));
    }
}
