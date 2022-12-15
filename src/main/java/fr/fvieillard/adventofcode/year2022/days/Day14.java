package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.fvieillard.adventofcode.year2022.Day2022;

import static fr.fvieillard.adventofcode.year2022.days.Day14.Point.Type.ROCK;
import static fr.fvieillard.adventofcode.year2022.days.Day14.Point.Type.SAND;
import static fr.fvieillard.adventofcode.year2022.days.Day14.Point.Type.SOURCE;


public class Day14 extends Day2022 {
    public Day14(InputStream input) {
        super(14, "Regolith Reservoir", input);
    }

    public static void main(String... args) {
        new Day14(Day14.class.getResourceAsStream("day_14.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        Grid grid = new Grid(getInput(), new Point(500, 0, SOURCE));
        return grid.simulate();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    record Point(int x, int y, Type type) implements Comparable<Point> {
        enum Type {
            ROCK('#'), SAND('o'), SOURCE('+');
            Character representation;

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
    }


    static class Grid {
        private SortedSet<Point> points = new TreeSet<>();
        private Point source;
        private int minX = Integer.MAX_VALUE, maxX = 0, minY = Integer.MAX_VALUE, maxY = 0;

        Grid(String input, Point source) {
            this.source = source;
            addPoint(source);
            new Scanner(input).useDelimiter("\n").forEachRemaining(s -> {
                Scanner line = new Scanner(s).useDelimiter(" -> ");
                int[] current = Arrays.stream(line.next().split(",")).mapToInt(Integer::parseInt).toArray();
                while (line.hasNext()) {
                    int[] next = Arrays.stream(line.next().split(",")).mapToInt(Integer::parseInt).toArray();
                    int startX = Math.min(current[0], next[0]);
                    int endX = Math.max(current[0], next[0]);
                    int startY = Math.min(current[1], next[1]);
                    int endY = Math.max(current[1], next[1]);
                    for (int c = startX; c <= endX; c++) {
                        for (int r = startY; r <= endY; r++) {
                            addPoint(new Point(c, r, ROCK));
                        }
                    }
                    current = next;
                }
            });
        }

        int simulate() {
            int i = 0;
            boolean insideGrid = true;
            while (insideGrid) {
                Point currentSand = source;
                while (true) {
                    Point nextPos = new Point(currentSand.x, currentSand.y + 1, SAND);
                    if (points.contains(nextPos)) {
                        nextPos = new Point(currentSand.x - 1, currentSand.y + 1, SAND);
                    }
                    if (points.contains(nextPos)) {
                        nextPos = new Point(currentSand.x + 1, currentSand.y + 1, SAND);
                    }
                    if (points.contains(nextPos)) {
                        // currentSand is at rest
                        points.add(currentSand);
                        break;
                    } else if (nextPos.x < minX || nextPos.x > maxX || nextPos.y > maxY) {
                        insideGrid = false;
                        break;
                    }
                    currentSand = nextPos;
                }
                i++;
                //draw();
            }
            return i - 1;
        }

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
    }
}
