package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day03 extends Day2023 {
    Set<Symbol> symbols = new TreeSet<>();
    Set<PartNumber> parts = new TreeSet<>();


    public Day03(InputStream input) {
        super(1, "Gear Ratios", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day03(Day03.class.getResourceAsStream("day_03.txt")).printDay();
    }

    void parseInput() {
        Pattern partNumberPattern = Pattern.compile("\\d+");
        Pattern symbolPattern = Pattern.compile("[^.\\d]");
        int y = 0;
        for (String line : getInput().split("\n")) {
            final int lineNumber = y;
            parts.addAll(partNumberPattern.matcher(line).results()
                    .map(matchResult -> new PartNumber(
                            Integer.valueOf(matchResult.group()),
                            new Point2D(matchResult.start(), lineNumber),
                            matchResult.end() - matchResult.start()))
                    .toList());
            symbols.addAll(symbolPattern.matcher(line).results()
                    .map(matchResult -> new Symbol(
                            matchResult.group(),
                            new Point2D(matchResult.start(), lineNumber)))
                    .toList());
            y++;
        }
        System.err.println("partNumbers:" + parts);
        System.err.println("symbols:" + symbols);
    }

    @Override
    public Object getSolutionPart1() {
        return parts.parallelStream().filter(part -> {
            long minY = part.startPosition.y - 1;
            long maxY = part.startPosition.y + 1;
            long minX = part.startPosition.x - 1;
            long maxX = minX + part.length + 1;

            return symbols.parallelStream().anyMatch(symbol -> {
                long y2 = symbol.position.y();
                long x2 = symbol.position.x();
                return y2 >= minY && y2 <= maxY && x2 >= minX && x2 <= maxX;
            });
        }).mapToInt(PartNumber::value).sum();
        }

    @Override
    public Object getSolutionPart2() {
        long result = 0;
        for (Symbol symbol:symbols) {
            long minY = symbol.position.y - 1;
            long maxY = symbol.position.y + 1;
            long minX = symbol.position.x - 1;
            long maxX = symbol.position.x + 1;
            List<PartNumber> adjacentParts = parts.parallelStream()
                    .filter(partNumber -> {
                        long y2 = partNumber.startPosition.y();
                        long x2 = partNumber.startPosition.x();
                        long x3 = partNumber.startPosition.x() + partNumber.length - 1;
                        return y2 >= minY && y2 <= maxY && x3 >= minX && x2 <= maxX;
                    }).toList();

            if (adjacentParts.size() == 2) {
                long ratio = adjacentParts.get(0).value * adjacentParts.get(1).value;
                System.err.println(String.format("Found gear around symbol %s, with parts %s. --> Gear Ratio: %s", symbol, adjacentParts, ratio));
                result += ratio;
            }
        }
        return result;
    }


    record Symbol(String value, Point2D position) implements Comparable<Symbol> {
        @Override
        public int compareTo(final Symbol o) {
            return position().compareTo(o.position());
        }
    }

    record PartNumber(Integer value, Point2D startPosition, Integer length) implements Comparable<PartNumber> {
        @Override
        public int compareTo(final PartNumber o) {
            return startPosition().compareTo(o.startPosition());
        }
    }

    record Point2D(long x, long y) implements Comparable<Point2D> {

        @Override
        public int compareTo(final Point2D o) {
            return Comparator.comparing(Point2D::y).thenComparing(Point2D::x).compare(this, o);
        }
    }
}
