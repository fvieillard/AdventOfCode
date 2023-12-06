package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Day06 extends Day2023 {

    public Day06(InputStream input) {
        super(6, "Wait For It", input);
    }

    public static void main(String... args) {
        new Day06(Day06.class.getResourceAsStream("day_06.txt")).printDay();
    }


    @Override
    public Object getSolutionPart1() {
        List<Race> races = new ArrayList<>();

        String[] lines = getInput().split("\n");
        String[] times = lines[0].replaceAll("Time:\\s*","").split(" +");
        String[] distances = lines[1].replaceAll("Distance:\\s*","").split(" +");

        for (int i = 0; i < times.length; i++) {
            races.add(new Race(Long.valueOf(times[i]), Long.valueOf(distances[i])));
        }

        System.out.println(races);

        return races.stream().mapToLong(Race::waysToWin).reduce((left, right) -> left * right).getAsLong();
    }

    @Override
    public Object getSolutionPart2() {
        String[] lines = getInput().split("\n");

        Race race = new Race(Long.valueOf(lines[0].replaceAll("Time:| *","")),
        Long.valueOf(lines[1].replaceAll("Distance:| *","")));

        System.out.println(race);

        return race.waysToWin();
    }


    record Race(Long duration, Long recordDistance) {
        boolean isWinner(Long timePressed) {
            return timePressed * (duration - timePressed) > recordDistance;
        }

        Long[] boudariesToWin() {
            Long min = null, max = null;
            for (long i = 0; i < duration; i++) {
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
            return new Long[]{min, max};
        }

        Long waysToWin() {
            Long[] boudaries = boudariesToWin();
            return boudaries[1] - boudaries[0] + 1;
        }
    }
}
