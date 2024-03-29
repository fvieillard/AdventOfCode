package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day11 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();
    SortedSet<Point2D> universe = new TreeSet<>();
    SortedSet<Integer> rowsWithoutGalaxy = new TreeSet<>();
    SortedSet<Integer> columnsWithoutGalaxy = new TreeSet<>();

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
        rowsWithoutGalaxy = new TreeSet<>(IntStream.range(0, height).mapToObj(Integer::valueOf).toList());
        columnsWithoutGalaxy = new TreeSet<>(IntStream.range(0, width).mapToObj(Integer::valueOf).toList());

        int y = 0;
        Pattern galaxyPattern = Pattern.compile("#");

        for (String line : lines) {
            LOG.debug("Line {}: {}", y, line);
            Matcher matcher = galaxyPattern.matcher(line);
            List<MatchResult> results = matcher.results().toList();
            for (MatchResult result : results) {
                LOG.debug("Found galaxy at {}", new Point2D(y, result.start()));
                rowsWithoutGalaxy.remove(y);
                columnsWithoutGalaxy.remove(result.start());
                universe.add(new Point2D(y, result.start()));
            }
            y++;
        }

        LOG.debug("State of universe: {}", universe);
    }


    public Set<Point2D> expandedUniverse(int expansionFactor) {
        LOG.debug("No galaxy in rows: {}. expanding...", rowsWithoutGalaxy);
        LOG.debug("No galaxy in colums: {}. expanding...", columnsWithoutGalaxy);


        Set<Point2D> expandedUniverse = new TreeSet<>();
        for (Point2D point : universe) {

            Point2D expandedPoint = new Point2D(
                    point.y + expansionFactor * rowsWithoutGalaxy.headSet(point.y).size(),
                    point.x + expansionFactor * columnsWithoutGalaxy.headSet(point.x).size());
            expandedUniverse.add(expandedPoint);
            LOG.debug(point + " --> " + expandedPoint);
        }
        return expandedUniverse;
    }

    public static long sumOfDistances(Collection<Point2D> universe) {
        List<Point2D> galaxies = new ArrayList<>(universe);
        long totalDistance = 0;
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                Point2D galaxy2 = galaxies.get(j);
                Point2D galaxy1 = galaxies.get(i);
                int distance = Math.abs(galaxy2.y - galaxy1.y) + Math.abs(galaxy2.x - galaxy1.x);
                LOG.debug("Distance from {} to {} = {}", galaxy1, galaxy2, distance);
                totalDistance+=distance;
            }
        }
        return totalDistance;
    }

    @Override
    public Object getSolutionPart1() {
        return sumOfDistances(expandedUniverse(1));
    }


    public Object getSolutionPart2() {
        return sumOfDistances(expandedUniverse(999999));
    }

    record Point2D(int y, int x) implements Comparable<Point2D> {

        @Override
        public int compareTo(final Point2D o) {
            return Comparator.comparing(Point2D::y).thenComparing(Point2D::x).compare(this, o);
        }
    }
}
