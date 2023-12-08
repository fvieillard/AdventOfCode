package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day04 extends Day2023 {

    public Day04(InputStream input) {
        super(4, "Scratchcards", input);
        processInput();
    }

    public static void main(String... args) {
        new Day04(Day04.class.getResourceAsStream("day_04.txt")).printDay();
    }


    @Override
    public Object getSolutionPart1() {
        return getInput().lines().mapToInt(s -> {
            String cardNumber = s.substring(5, s.indexOf(':')).trim();
            List<String> winning = Arrays.asList(s.substring(s.indexOf(':') + 1, s.indexOf('|')).trim().split("\\s+"));
            List<String> played = new ArrayList<>(Arrays.asList(s.substring(s.indexOf('|') + 1).trim().split("\\s+")));
            System.err.println(String.format("Card %s has winning numbers: %s and numbers: %s", cardNumber, winning, played));

            played.retainAll(winning);
            int nbWins = played.size();

            int score = nbWins == 0 ? 0 : Math.toIntExact(Math.round(Math.pow(2, nbWins - 1)));
            System.err.println(nbWins + " winning numbers --> score = " + score);

            return score;
        }).sum();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


}
