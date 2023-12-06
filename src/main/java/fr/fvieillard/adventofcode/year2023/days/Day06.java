package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day06 extends Day2023 {
    List<Race> races = new ArrayList<>();

    public Day06(InputStream input) {
        super(6, "Wait For It", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day06(Day06.class.getResourceAsStream("day_06.txt")).printDay();
    }

    void parseInput() {
        String[] lines = getInput().split("\n");

        String[] times = lines[0].replaceAll("Time:\\s*","").split(" +");
        String[] distances = lines[1].replaceAll("Distance:\\s*","").split(" +");

        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Integer.valueOf(times[i]), Integer.valueOf(distances[i])));
        }

        System.out.println(races);
    }

    @Override
    public Object getSolutionPart1() {
        return races.stream().mapToInt(Race::waysToWin).reduce((left, right) -> left * right).getAsInt();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    record Race(Integer duration, Integer recordDistance) {
        boolean isWinner(Integer timePressed) {
            return timePressed * (duration - timePressed) > recordDistance;
        }

        Integer[] boudariesToWin() {
            Integer min = null, max = null;
            for (int i = 0; i < duration; i++) {
                if (isWinner(i)) {
                    if (min == null) {
                        min = i;
                    }
                    max = i;
                } else if (max != null) {
                    System.out.println(String.format("Stopping loop as we reached the second boudary. [%s -> %s]", min, max));
                    break;
                }
            }
            return new Integer[]{min, max};
        }

        Integer waysToWin() {
            Integer[] boudaries = boudariesToWin();
            return boudaries[1] - boudaries[0] + 1;
        }
    }
}
