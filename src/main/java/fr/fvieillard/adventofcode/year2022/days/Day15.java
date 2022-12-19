package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day15 extends Day2022 {
    private static final Pattern REPORT_LINE = Pattern.compile("""
            Sensor at x=(?<SensorX>-?\\d+), y=(?<SensorY>-?\\d+): \
            closest beacon is at x=(?<BeaconX>-?\\d+), y=(?<BeaconY>-?\\d+)""");

    private Grid grid;

    public Day15(InputStream input) {
        super(15, "Beacon Exclusion Zone", input);
    }

    public static void main(String... args) {
        new Day15(Day15.class.getResourceAsStream("day_15.txt")).printDay();
    }

    protected void processInput() {
        List<Point[]> sensorsAndBeacons = new ArrayList<>();

        grid = new Grid();
        Matcher matcher = REPORT_LINE.matcher(getInput());

        while (matcher.find()) {
            Point sensor = new Point(
                    Integer.parseInt(matcher.group("SensorX")),
                    Integer.parseInt(matcher.group("SensorY")),
                    Point.Type.SENSOR);
            Point nearestBeacon = new Point(
                    Integer.parseInt(matcher.group("BeaconX")),
                    Integer.parseInt(matcher.group("BeaconY")),
                    Point.Type.BEACON);

            sensorsAndBeacons.add(new Point[]{sensor, nearestBeacon});
            grid.addPoint(sensor);
            grid.addPoint(nearestBeacon);
        }

        for (Point[] couple : sensorsAndBeacons) {
            Point sensor = couple[0];
            Point beacon = couple[1];
            int distance = sensor.manhattanDistanceTo(beacon);
//            System.out.printf("%s - %s, distance: %s%n", sensor, beacon, distance);

            // Calculate cells cleared on line 2000000 only (no need to do the rest)
            int targetY = 2000000;
            int offsetY = Math.abs(targetY - sensor.y);
            for (int x = 0; x <= distance - offsetY; x++) {
                grid.addPoint(new Point(sensor.x + x, targetY, Point.Type.CLEARED));
                grid.addPoint(new Point(sensor.x - x, targetY, Point.Type.CLEARED));
            }
            //grid.draw();
        }

//        grid.draw();
    }

    @Override
    public Object getSolutionPart1() {
        return grid.getPoints().stream()
                .filter(point -> point.y == 2000000 && point.type != Point.Type.BEACON)
                .count();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    record Point(int x, int y, Type type) implements Comparable<Point> {
        enum Type {
            SENSOR('S'), BEACON('B'), CLEARED('#');
            final Character representation;

            Type(final Character representation) {
                this.representation = representation;
            }
        }

        @Override
        public int compareTo(final Point o) {
            if (o.y == y) {
                return x - o.x;
            }
            return y - o.y;
        }

        @Override
        public String toString() {
            return type + "(" + x + "," + y + ")";
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj instanceof Point point) {
                return point.x == x && point.y == y;
            }
            return false;
        }


        public int manhattanDistanceTo(Point p2) {
            return Math.abs(x - p2.x) + Math.abs(y - p2.y);
        }
    }


    static class Grid {
        private SortedSet<Point> points = new TreeSet<>();
        private int minX = Integer.MAX_VALUE, maxX = 0, minY = Integer.MAX_VALUE, maxY = 0;

        private void addPoint(Point point) {
            points.add(point);
            minX = Math.min(minX, point.x);
            maxX = Math.max(maxX, point.x);
            minY = Math.min(minY, point.y);
            maxY = Math.max(maxY, point.y);
        }

        void draw() {
            System.out.printf("%s Points: %s%n", points.size(), points);
            System.out.printf("From (%s,%s) to (%s,%s)%n", minX, minY, maxX, maxY);
            int cursorX = minX - 1;
            int cursorY = minY;
            StringBuilder result = new StringBuilder();
            for (Point point : points) {
                while (point.y > cursorY) {
                    result.append(".".repeat(maxX - cursorX)).append("\n");
                    cursorY++;
                    cursorX = minX - 1;
                }
                result.append(".".repeat(point.x - cursorX - 1));
                result.append(point.type.representation);
                cursorX = point.x;
            }
            System.out.println(result);
        }

        public Collection<Point> getPoints() {
            return Collections.unmodifiableSet(points);
        }
    }
}
