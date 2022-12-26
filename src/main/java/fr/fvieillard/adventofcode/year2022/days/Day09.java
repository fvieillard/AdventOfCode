package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day09 extends Day2022 {

    private List<Move> moves = new ArrayList<>();

    public Day09(InputStream input) {
        super(9, "Rope Bridge", input);
    }

    public static void main(String... args) {
        new Day09(Day09.class.getResourceAsStream("day_09.txt")).printDay();
    }

    enum Direction {
        UP(1,0), DOWN(-1,0), LEFT(0,-1), RIGHT(0,1);

        final int verticalMove;
        final int horizontalMove;

        static Direction parse(String dir) {
            return switch (dir) {
                case "U" -> UP;
                case "D" -> DOWN;
                case "L" -> LEFT;
                case "R" -> RIGHT;
                default -> throw new IllegalArgumentException(dir);
            };
        }

        Direction(int verticalMove, int horizontalMove) {
            this.verticalMove = verticalMove;
            this.horizontalMove = horizontalMove;
        }

    }
    record Point(int x, int y) {
        double distance(Point to) {
            return Math.sqrt((to.x - x) * (to.x - x) + (to.y - y) * (to.y - y));
        }

        @Override
        public String toString() {
            return "(" + x + ";" + y + ")";
        }

        public Point move(Direction dir) {
            return new Point(
                    x + dir.horizontalMove,
                    y + dir.verticalMove
            );
        }
    }
    record Move(Direction dir, int distance) {
        static Move parse(String move) {
            String[] directionAndDistance = move.split(" ");
            return new Move(
                    Direction.parse(directionAndDistance[0]),
                    Integer.parseInt(directionAndDistance[1])
                    );
        }

        @Override
        public String toString() {
            return dir + "(" + distance + ")";
        }
    }


    @Override
    protected void processInput() {
        moves = getInput().lines().map(Move::parse).toList();
        System.out.printf("List of moves: %s%n", moves);
    }

    @Override
    public Object getSolutionPart1() {
        Point start = new Point(0,0);
        Point head = start;
        Point tail = start;
        Set<Point> positionsVisited = new HashSet<>();
        positionsVisited.add(tail);
        for (Move move:moves) {
            System.out.printf("Head%s, Tail%s  -->  %s%n", head, tail, move);
            for (int step=0; step < move.distance; step++) {
                head = head.move(move.dir);
                System.out.printf("  -->  %s", head);

                if (tail.distance(head) > Math.sqrt(2)) {
                    tail = tail.move(move.dir);
                    // tail should be at exactly one or else we realign horizontally or vertically
                    if (tail.distance(head) > 1) {
                        tail = switch (move.dir) {
                            case UP, DOWN -> new Point(head.x, tail.y);
                            case LEFT, RIGHT ->  new Point(tail.x, head.y);
                        };
                    }
                    System.out.printf(", move tail to %s%n", tail);
                    positionsVisited.add(tail);
                } else {
                    System.out.printf(", do nothing%n");
                }
            }
        }
        return positionsVisited.size();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }
}
