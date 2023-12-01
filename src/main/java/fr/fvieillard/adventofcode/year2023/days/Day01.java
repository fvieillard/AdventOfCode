package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day01 extends Day2023  {
    public Day01(InputStream input) {
        super(1, "Trebuchet?!", input);
    }

    public static void main(String... args) {
        new Day01(Day01.class.getResourceAsStream("day_01.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return getInput()
                .replaceAll("[^\\d\\r\\n]","")
                .lines()
                .mapToInt(value -> {
                    return Integer.valueOf(value.substring(0, 1) + value.substring(value.length() - 1));
                })
                .sum();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }
}
