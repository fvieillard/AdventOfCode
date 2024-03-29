package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day07 extends Day2023 {

    enum Card {
        CARD_A, CARD_K, CARD_Q, CARD_J, CARD_T, CARD_9, CARD_8, CARD_7, CARD_6, CARD_5, CARD_4, CARD_3, CARD_2;

        public static Card parse(char s) {
            return Card.valueOf("CARD_" + s);
        }

        @Override
        public String toString() {
            return name().substring(5);
        }
    }

    enum HandType {
        FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIR, ONE_PAIR, HIGH_CARD
    }

    static class Hand implements Comparable<Hand> {
        Card c1, c2, c3, c4, c5;
        HandType type;

        public Hand(final String s) {
            c1 = Card.parse(s.charAt(0));
            c2 = Card.parse(s.charAt(1));
            c3 = Card.parse(s.charAt(2));
            c4 = Card.parse(s.charAt(3));
            c5 = Card.parse(s.charAt(4));

            Map<Card, Long> countsByCard = Stream.of(c1, c2, c3, c4, c5)
                    .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
            Collection<Long> counts = countsByCard.values();

            if (counts.contains(5L)) {
                type = HandType.FIVE_OF_A_KIND;
            } else if (counts.contains(4L)) {
                type = HandType.FOUR_OF_A_KIND;
            } else if (counts.contains(3L)) {
                if (counts.size() == 2) {
                    type = HandType.FULL_HOUSE;
                } else {
                    type = HandType.THREE_OF_A_KIND;
                }
            } else if (counts.contains(2L)) {
                if (counts.size() == 3) {
                    type = HandType.TWO_PAIR;
                } else {
                    type = HandType.ONE_PAIR;
                }
            } else {
                type = HandType.HIGH_CARD;
            }
        }

        @Override
        public String toString() {
            return "" + c1 + c2 + c3 + c4 + c5 + " -> " + type;
        }

        @Override
        public int compareTo(final Hand o) {
            return Comparator
                    .comparing((Hand hand) -> hand.type)
                    .thenComparing(hand -> hand.c1)
                    .thenComparing(hand -> hand.c2)
                    .thenComparing(hand -> hand.c3)
                    .thenComparing(hand -> hand.c4)
                    .thenComparing(hand -> hand.c5)
                    .compare(this, o);
        }
    }

    record Row(Hand hand, Integer bid) implements Comparable<Row> {

        @Override
        public int compareTo(final Row o) {
            return hand.compareTo(o.hand);
        }
    }

    private final List<Row> rows = new ArrayList<>();

    public Day07(InputStream input) {
        super(7, "Camel Cards", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day07(Day07.class.getResourceAsStream("day_07.txt")).printDay();
    }


    protected void parseInput() {
        getInput().lines().forEach(s -> {
            String[] input = s.split(" ");
            rows.add(new Row(new Hand(input[0]), Integer.valueOf(input[1])));
        });
    }

    @Override
    public Object getSolutionPart1() {
        rows.sort(Comparator.reverseOrder());
        int i = 0;
        long sum = 0;
        for (Row r : rows) {
            i++;
            sum += i * r.bid;
            LOG.debug("{},  {} * {}", r.hand, i, r.bid);
        }
        return sum;
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }
}
