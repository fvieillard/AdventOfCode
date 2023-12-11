package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day11 extends Day2023 {


    Set<Point2D> expandedUniverse = new TreeSet<>();

    public Day11(InputStream input) {
        super(11, "Cosmic Expansion", input);
        parseUniverse();
    }

    public static void main(String... args) {
        new Day11(Day11.class.getResourceAsStream("day_11.txt")).printDay();
    }


    void parseUniverse() {
        String[] lines = getInput().split("\n");
        int height = lines.length, width = lines[0].length();

        int y = 0;
        Pattern galaxyPattern = Pattern.compile("#");

        Set<Point2D> universe = new TreeSet<>();
        Set<Integer> columsWithoutGalaxy = new TreeSet<>(IntStream.range(0, width).mapToObj(Integer::valueOf).toList());
        for (String line : lines) {
            System.err.println("Line " + y + ": " + line);
            Matcher matcher = galaxyPattern.matcher(line);
            List<MatchResult> results = matcher.results().toList();
            if (results.isEmpty()) {
                // If no galaxy in this line, we can already expand vertically and add one more increment to y
                System.err.println("No galaxy in line, expanding...");
                y++;
            }
            for (MatchResult result : results) {
                System.err.println("Found galaxy at " + new Point2D(y, result.start()));
                columsWithoutGalaxy.remove(result.start());
                universe.add(new Point2D(y, result.start()));
            }
            y++;
        }
        System.err.println("No galaxy in colums: " + columsWithoutGalaxy + ". expanding...");

        for (Point2D point : universe) {
            Point2D expandedPoint = new Point2D(point.y,
                    point.x + Math.toIntExact(columsWithoutGalaxy.stream()
                            .filter(integer -> integer < point.x)
                            .count()));
            expandedUniverse.add(expandedPoint);
            System.err.println(point + " --> " + expandedPoint);
        }

        System.err.println("State of expanded universe: " + expandedUniverse);
    }

    @Override
    public Object getSolutionPart1() {
        List<Point2D> galaxies = new ArrayList<>(expandedUniverse);
        int totalDistance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Point2D galaxy2 = galaxies.get(j);
                Point2D galaxy1 = galaxies.get(i);
                int distance = Math.abs(galaxy2.y - galaxy1.y) + Math.abs(galaxy2.x - galaxy1.x);
                System.err.println("Distance from " + galaxy1 + " to " + galaxy2 + " = " + distance);
                totalDistance+=distance;
            }
        }
        return totalDistance;
    }


    public Object getSolutionPart2() {
        return null;
    }

    record Point2D(int y, int x) implements Comparable<Point2D> {

        @Override
        public int compareTo(final Point2D o) {
            return Comparator.comparing(Point2D::y).thenComparing(Point2D::x).compare(this, o);
        }
    }
}
