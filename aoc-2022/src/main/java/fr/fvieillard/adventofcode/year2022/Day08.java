package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day08 extends Day2022 {

    int[][] forest;
    int[][] visibility;
    int[][] scenicScore;
    int height;
    int width;
    int scannedTrees = 0;

    public Day08(InputStream input) {
        super(8, "Treetop Tree House", input);
        processInput();
    }

    public static void main(String... args) {
        new Day08(Day08.class.getResourceAsStream("day_08.txt")).printDay();
    }


    protected void processInput() {
        // Parse input into Forest grid
        List<int[]> tempList = new ArrayList<>();
        getInput().lines().forEachOrdered(
                line -> tempList.add(
                        line.chars().map(c -> c - 48).toArray()
                )
        );

        height = tempList.size();
        width = tempList.get(0).length;
        forest = new int[height][width];

        for (int y = 0; y < height; y++) {
            forest[y] = tempList.get(y);
        }
//        System.out.printf("Forest:%n%s", printMatrix(forest));
    }

    @Override
    public Object getSolutionPart1() {
        visibility = new int[height][width];
        // For each row, get the trees visible from the left or right
        for (int y = 0; y < height; y++) {
            int fromLeft = getVisibleTreesInRow(y, 0, width - 1);
            int fromRight = getVisibleTreesInRow(y, width - 1, 0);
//            System.out.printf("Line %s, visible from left: %s, from right: %s%n", y, fromLeft, fromRight);
        }
        for (int x = 0; x < width; x++) {
            int fromTop = getVisibleTreesInColumn(x, 0, height - 1);
            int fromBottom = getVisibleTreesInColumn(x, height - 1, 0);
//            System.out.printf("Column %s, visible from top: %s, from bottom: %s%n", x, fromTop, fromBottom);
        }

//        System.out.printf("End, scanned trees: %s%n", scannedTrees);
//        System.out.printf("Visibility:%n%s", printMatrix(visibility));

        // Sum of all visible trees:
        return Arrays.stream(visibility).mapToInt(
                row -> Arrays.stream(row).sum()
        ).sum();
    }

    @Override
    public Object getSolutionPart2() {
        scenicScore = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                scenicScore[y][x] = calculateScenicScore(y, x);
            }
        }

//        System.out.printf("Scenix Score: %n%s%n", printMatrix(scenicScore));
        // Max of all scenic score:
        return Arrays.stream(scenicScore).mapToInt(
                row -> Arrays.stream(row).max().orElse(0)
        ).max().orElse(0);
    }

    private int calculateScenicScore(final int y, final int x) {
        return
                viewingDistanceRight(y, x)
                * viewingDistanceBottom(y, x)
                * viewingDistanceLeft(y, x)
                * viewingDistanceTop(y, x);
    }

    private int viewingDistanceTop(final int y, final int x) {
        int treeSize = forest[y][x];
        for (int i = 1; i <= y; i++) {
            if (forest[y - i][x] >= treeSize) return i;
        }
        return y;
    }
    private int viewingDistanceBottom(final int y, final int x) {
        int treeSize = forest[y][x];
        for (int i = 1; i < height - y - 1; i++) {
            if (forest[y + i][x] >= treeSize) return i;
        }
        return height - y - 1;
    }
    private int viewingDistanceRight(final int y, final int x) {
        int treeSize = forest[y][x];
        for (int i = 1; i <= width - x - 1; i++) {
            if (forest[y][x + i] >= treeSize) return i;
        }
        return width - x - 1;
    }
    private int viewingDistanceLeft(final int y, final int x) {
        int treeSize = forest[y][x];
        for (int i = 1; i < x; i++) {
            if (forest[y][x - i] >= treeSize) return i;
        }
        return x;
    }


    private int getVisibleTreesInRow(int row, int from, int to) {
        int step = Math.round(Math.signum(to - from));
        int maxHeight = -1;
        int result = 0;
        for (int x = from; to - x * step >= 0; x+=step) {
            scannedTrees++;
            int currentHeight = forest[row][x];
            if (currentHeight > maxHeight) {
                maxHeight = currentHeight;
                visibility[row][x] = 1;
                result++;
                if (currentHeight == 9) {
                    break;
                }
            }
        }
        return result;
    }
    private int getVisibleTreesInColumn(int column, int from, int to) {
        int step = Math.round(Math.signum(to - from));
        int maxHeight = -1;
        int result = 0;
        for (int x = from; to - x * step >= 0; x+=step) {
            scannedTrees++;
            int currentHeight = forest[x][column];
            if (currentHeight > maxHeight) {
                maxHeight = currentHeight;
                visibility[x][column] = 1;
                result++;
                if (currentHeight == 9) { // No need to go further, 9 is the max
                    break;
                }
            }
        }
        return result;
    }

    private static final String printMatrix(int[][] matrix) {
        StringBuilder result = new StringBuilder();
        for (final int[] ints : matrix) {
            result.append("[");
            int width = ints.length;
            for (final int cell : ints) {
                result.append(cell).append(", ");
            }
            result.append(ints[width - 1]).append("]\n");
        }
        return result.toString();
    }
}
