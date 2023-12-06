package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Day09 extends Day2022 {

    private List<Move> moves = new ArrayList<>();

    public Day09(InputStream input) {
        super(9, "Rope Bridge", input);
        processInput();
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


    protected void processInput() {
        moves = getInput().lines().map(Move::parse).toList();
//        System.out.printf("List of moves: %s%n", moves);
    }

    private int simulateRope(int numberOfKnots) {
        Point start = new Point(0,0);
        Point[] knots = new Point[numberOfKnots];
        for (int i=0; i<numberOfKnots; i++) knots[i] = start;
        Set<Point> positionsVisited = new HashSet<>();
        positionsVisited.add(knots[numberOfKnots-1]);
        for (Move move:moves) {
//            System.out.printf("Head%s  -->  %s%n", knots[0], move);
            for (int step=0; step < move.distance; step++) {
                knots[0] = knots[0].move(move.dir);
                for (int knot = 0; knot < numberOfKnots - 1; knot++) {
                    Point currentKnot = knots[knot];
                    Point nextKnot = knots[knot + 1];

//                    System.out.printf("   Knot %s: %s  -->  ", knot, knots[knot]);

                    if (nextKnot.distance(currentKnot) > Math.sqrt(2)) {
                        nextKnot = new Point(
                                nextKnot.x + (int)Math.signum(currentKnot.x - nextKnot.x),
                                nextKnot.y + (int)Math.signum(currentKnot.y - nextKnot.y)
                        );
//                        System.out.printf("move knot %s to %s%n", knot + 1, nextKnot);
                        knots[knot + 1] = nextKnot;
                    } else {
//                        System.out.printf("do nothing%n");
                    }
                }
                positionsVisited.add(knots[numberOfKnots - 1]);
            }
        }
        return positionsVisited.size();
    }

    @Override
    public Object getSolutionPart1() {
        return simulateRope(2);
    }

    @Override
    public Object getSolutionPart2() {
        return simulateRope(10);
    }
}
