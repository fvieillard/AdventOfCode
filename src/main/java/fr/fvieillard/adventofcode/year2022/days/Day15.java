package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day15 extends Day2022 {
    private static final Pattern REPORT_LINE = Pattern.compile("""
            Sensor at x=(?<SensorX>-?\\d+), y=(?<SensorY>-?\\d+): \
            closest beacon is at x=(?<BeaconX>-?\\d+), y=(?<BeaconY>-?\\d+)""");

    private Collection<Sensor> sensors;
    private Collection<Point> beacons;

    public Day15(InputStream input) {
        super(15, "Beacon Exclusion Zone", input);
    }

    public static void main(String... args) {
        new Day15(Day15.class.getResourceAsStream("day_15.txt")).printDay();
    }

    protected void processInput() {
        sensors = new HashSet<>();
        beacons = new HashSet<>();

        Matcher matcher = REPORT_LINE.matcher(getInput());

        while (matcher.find()) {
            int sX = Integer.parseInt(matcher.group("SensorX"));
            int sY = Integer.parseInt(matcher.group("SensorY"));
            int bX = Integer.parseInt(matcher.group("BeaconX"));
            int bY = Integer.parseInt(matcher.group("BeaconY"));

            sensors.add(new Sensor(sX, sY, Math.abs(bX - sX) + Math.abs(bY - sY)));
            beacons.add(new Point(bX, bY));
        }
    }

    @Override
    public Object getSolutionPart1() {
        List<Range> ranges = new ArrayList<>();
        // On the target row, we find the range covered by each sensor (cells cleared),
        // then we calculate the union of all the ranges, finding the total number of cells cleared.
        int row = 2000000;
//        int row = 10;
        for (Sensor sensor:sensors) {
            int distanceY = Math.abs(sensor.y - row);
            int distanceX = sensor.distance - distanceY;
            if (distanceX > 0) {
                Range rangeClearedBySensor = new Range(sensor.x - distanceX, sensor.x + distanceX);
//                System.out.printf("On row %s, %s clears %s%n", row, sensor, rangeClearedBySensor);
                ranges.add(rangeClearedBySensor);
            } else {
//                System.out.printf("On row %s, %s clears no cell%n", row, sensor);
            }

        }

        // Result is the sum of size of all the reduced ranges (number of cells cleared)
        // minus the number of beacons that actually contain a beacon that we know of
        return Range.reduce(ranges).stream()
                       .mapToInt(Range::size)
                       .sum()
               - beacons.stream()
                       .filter(point -> point.y == row)
                       .count();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }

    record Range(int from, int to) {
        int size() {
            return to - from + 1;
        }

        boolean overlap(Range other) {
            return !(other.to < from || other.from > to);
        }

        static Collection<Range> reduce(Collection<Range> ranges) {
            // Sorting the ranges by their 'from' guarantees that if 2 ranges overlap
            // they will come in sequence.
            List<Range> sortedRanges = new ArrayList<>(ranges);
            sortedRanges.sort(Comparator.comparingInt((Range o) -> o.from));
//            System.out.printf("Sorted ranges: %s%n", sortedRanges);

            // Since ranges are sorted, we can unionize each new range with the previously
            // reduced range. If the new range doesn't overlap with the previous result,
            // we add the result to the list and start a new reduced Range.
            List<Range> result = new ArrayList<>();
            Range currentRange = sortedRanges.remove(0);
            for (Range range:sortedRanges) {
                if (range.overlap(currentRange)) {
                    currentRange = new Range(Math.min(currentRange.from, range.from), Math.max(range.to, currentRange.to));
                } else {
                    result.add(currentRange);
                    currentRange = range;
                }
            }
            result.add(currentRange);

//            System.out.printf("Reduced ranges: %s%n", result);

            return result;
        }
    }

    record Point(int x, int y) {}
    record Sensor(int x, int y, int distance) {}
}
