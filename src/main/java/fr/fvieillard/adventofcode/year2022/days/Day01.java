package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day01 extends Day2022 {

    private final List<Integer> calories = new ArrayList<>();

    public Day01(InputStream input) {
        super(1, "Calorie Counting", input);
    }

    public static void main(String... args) {
        new Day01(Day01.class.getResourceAsStream("day_01.txt")).printDay();
    }

    @Override
    protected void processInput() {
        int acc = 0;
        Scanner inputScanner = new Scanner(getInput());
        while (inputScanner.hasNextLine()) {
            final String line = inputScanner.nextLine();
            if ("".equals(line)) {
                calories.add(acc);
                acc = 0;
            } else {
                int calories = Integer.parseInt(line);
                acc = acc + calories;
            }
        }
        Collections.sort(calories);
        Collections.reverse(calories);
    }

    @Override
    public Object getSolutionPart1() {
        return calories.get(0);
    }

    @Override
    public Object getSolutionPart2() {
        return calories.subList(0, 3).stream().mapToInt(Integer::intValue).sum();
    }
}
