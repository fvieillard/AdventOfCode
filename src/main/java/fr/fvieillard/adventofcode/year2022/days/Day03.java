package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day03 extends Day2022 {

    private List<String> rucksacks = new ArrayList<>();

    public Day03(InputStream input) {
        super(3, "Rucksack Reorganization", input);
    }

    public static void main(String... args) {
        new Day03(Day03.class.getResourceAsStream("day_03.txt")).printDay();
    }

    @Override
    protected void processInput() {
        new Scanner(getInput()).useDelimiter("\n")
                .forEachRemaining(s -> rucksacks.add(s));
    }

    @Override
    public Object getSolutionPart1() {
        int total = 0;
        for (String line : rucksacks) {
            String rucksack1 = line.substring(0, line.length()/2);
            String rucksack2 = line.substring(line.length()/2);
//            System.out.printf("rucksack1: %s%nrucksack2: %s%n", rucksack1, rucksack2);

            char common = rucksack1.chars()
                    .filter(e1 -> rucksack2.chars().anyMatch(e2 -> e2 == e1))
                    .mapToObj(e -> (char)e)
                    .findFirst().get();
//            System.out.printf("Common type: %s, value: %s%n%n", common, getPriority(common));

            total += getPriority(common);
        }
        return total;
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }

    private static int getPriority(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        } else {
            return c - 'A' + 27;
        }
    }
}
