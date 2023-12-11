package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day04 extends Day2023 {
    public Day04(InputStream input) {
        super(4, "Scratchcards", input);
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
            LOG.debug("Card {} has winning numbers: {} and numbers: {}", cardNumber, winning, played);

            played.retainAll(winning);
            int nbWins = played.size();

            int score = nbWins == 0 ? 0 : Math.toIntExact(Math.round(Math.pow(2, nbWins - 1)));
            LOG.debug("{} winning numbers --> score = {}", nbWins, score);

            return score;
        }).sum();
    }

    @Override
    public Object getSolutionPart2() {
        Integer scores[] = new Integer[Math.toIntExact(getInput().lines().count())];
        for (String card : getInput().split("\n")) {
            Integer cardNumber = Integer.valueOf(card.substring(5, card.indexOf(':')).trim());
            List<String> winning = Arrays.asList(card.substring(card.indexOf(':') + 1, card.indexOf('|')).trim().split("\\s+"));
            List<String> played = new ArrayList<>(Arrays.asList(card.substring(card.indexOf('|') + 1).trim().split("\\s+")));
            played.retainAll(winning);
            scores[cardNumber - 1] = played.size();
        }
        LOG.debug(Arrays.toString(scores));

        int totalCountOfCards = 0;
        for (int i = 0; i < scores.length; i++) {
            totalCountOfCards += getCards(i, scores);
        }

        return totalCountOfCards;
    }


    private Integer getCards(Integer card, Integer[] scores) {
        int score = scores[card];
        int total = 1;

        for (int i = 1; i <= score; i++) {
            total += getCards(card + i, scores);
        }
        return total;
    }
}
