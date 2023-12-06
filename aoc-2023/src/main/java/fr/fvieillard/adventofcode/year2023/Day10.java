package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day10 extends Day2023 {

    public Day10(InputStream input) {
        super(10, "Pipe Maze", input);
    }

    public static void main(String... args) {
        new Day10(Day10.class.getResourceAsStream("day_10.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        // Parse input and find start
        int heigth = Math.toIntExact(getInput().lines().count());
        int width = getInput().indexOf('\n');
        char[][] map = new char[heigth][width];
        Point2D start = null;
        int y = 0;
        for (String line : getInput().split("\n")) {
            for (int x = 0; x < width; x++) {
                char symbol = line.charAt(x);
                map[y][x] = symbol;
                if (symbol == 'S') start = new Point2D(y, x);
            }
            System.err.println(Arrays.toString(map[y]));
            y++;
        }
        System.err.println("Starting search at: " + start);


        // Search
        Point2D previous = start;
        Point2D current = null;
        // Init search, find a direction where pipe is possible from start
        if (isPipeConnecting(start.up(), map, Arrays.asList('|', '7', 'F'))) {
            current = start.up();
        }
        if (isPipeConnecting(start.down(), map, Arrays.asList('|', 'L', 'J'))) {
            current = start.down();
        }
        if (isPipeConnecting(start.left(), map, Arrays.asList('-', 'L', 'F'))) {
            current = start.left();
        }
        if (isPipeConnecting(start.right(), map, Arrays.asList('-', '7', 'J'))) {
            current = start.right();
        }
        System.err.println("Found connecting pipe at " + current + ", following the path...");
        long length = 1;

        while (!current.equals(start)) {
            Point2D next = null;
            switch (map[current.y][current.x]) {
                case '|':
                    next = previous.equals(current.up()) ? current.down() : current.up();
                    break;
                case '-':
                    next = previous.equals(current.left()) ? current.right() : current.left();
                    break;
                case 'L':
                    next = previous.equals(current.up()) ? current.right() : current.up();
                    break;
                case 'J':
                    next = previous.equals(current.up()) ? current.left() : current.up();
                    break;
                case 'F':
                    next = previous.equals(current.down()) ? current.right() : current.down();
                    break;
                case '7':
                    next = previous.equals(current.down()) ? current.left() : current.down();
                    break;
                default:
                    System.err.println("Path boken at " + current + " coming from " + previous);
                    throw new IllegalArgumentException();
            }
            System.err.println("At " + current + "(" + map[current.y][current.x] + ") coming from " + previous + ". Next step is " + next + ". Path length so far: " + length);
            previous = current;
            current = next;
            length++;
        }

        System.err.println("Reached start again! Loop took " + length + " steps.");

        return length / 2;
    }


    public Object getSolutionPart2() {
        return null;
    }

    static boolean isPipeConnecting(Point2D pipe, char[][] map, List<Character> possibleValues) {
        if (pipe.x < 0 || pipe.y < 0 || pipe.x > map[0].length || pipe.y > map.length) {
            return false;
        }
        return possibleValues.contains(map[pipe.y][pipe.x]);
    }

    record Point2D(int y, int x) implements Comparable<Point2D> {

        @Override
        public int compareTo(final Point2D o) {
            return Comparator.comparing(Point2D::y).thenComparing(Point2D::x).compare(this, o);
        }

        public Point2D up() {
            return new Point2D(y - 1, x);
        }

        public Point2D down() {
            return new Point2D(y + 1, x);
        }

        public Point2D left() {
            return new Point2D(y, x - 1);
        }

        public Point2D right() {
            return new Point2D(y, x + 1);
        }
    }

}
