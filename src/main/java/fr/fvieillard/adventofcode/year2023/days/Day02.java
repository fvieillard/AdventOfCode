package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day02 extends Day2023 {

    private static Pattern patternGame = Pattern.compile("^Game (?<id>\\d+): (?<game>.*)$");
    private static Pattern patternDraw = Pattern.compile("((?<nbRed>\\d+) red)|((?<nbGreen>\\d+) green)|((?<nbBlue>\\d+) blue)");
    private static Integer maxRed = 12, maxGreen = 13, maxBlue = 14;

    private Map<Integer, List<Draw>> input = new HashMap<>();

    public Day02(InputStream input) {
        super(2, "Cube Conundrum", input);
        parseInput();
    }

    private void parseInput() {
        getInput().lines().forEach(s -> {
            Matcher matchGame = patternGame.matcher(s);
            matchGame.matches();
            Integer id = Integer.valueOf(matchGame.group("id"));
            String game = matchGame.group("game");
            List<Draw> draws = new ArrayList<>();
            input.put(id, draws);
            for (String draw : game.split(";")) {
                Integer nbRed = 0, nbGreen = 0, nbBlue = 0;
                for (String cubes : draw.split(",")) {
                    Integer nb = Integer.valueOf(cubes.trim().split(" ")[0]);
                    switch (cubes.trim().split(" ")[1]) {
                        case "red":
                            nbRed = nb;
                            break;
                        case "green":
                            nbGreen = nb;
                            break;
                        case "blue":
                            nbBlue = nb;
                            break;
                    }
                }
                draws.add(new Draw(nbRed, nbGreen, nbBlue));
            }
//            System.out.println("Game " + id + "  -  " + draws);
        });
    }

    public static void main(String... args) {
        new Day02(Day02.class.getResourceAsStream("day_02.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return input.entrySet().stream()
                .filter(integerListEntry -> {
                    return integerListEntry.getValue().stream()
                            .allMatch(Draw::isPossible);
                })
                .mapToInt(Map.Entry::getKey)
                .sum();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    private record Draw(Integer nbRed, Integer nbGreen, Integer nbBlue) {
        boolean isPossible() {
            return nbRed <= maxRed && nbGreen <= maxGreen && nbBlue <= maxBlue;
        }
    }
}
