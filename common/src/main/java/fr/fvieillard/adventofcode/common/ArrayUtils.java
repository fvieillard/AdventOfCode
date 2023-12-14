package fr.fvieillard.adventofcode.common;

public final class ArrayUtils {

    public static final char[][] stringToCharArray(String input) {
        String[] lines = input.lines().toArray(String[]::new);
        int rows = Math.toIntExact(lines.length);
        int cols = lines[0].length();

        char[][] array = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            array[i] = lines[i].toCharArray();
        }
        return array;
    }


    public static final char[][] rotateArray90Clockwise(char[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        char[][] rotatedArray = new char[cols][rows];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                rotatedArray[c][rows - r - 1] = input[r][c];
            }
        }
        return rotatedArray;
    }


    public static String charArrayToString(char[][] input) {
        int rows = input.length;
        int cols = input[0].length;

        StringBuilder builder = new StringBuilder();
        builder.append(String.valueOf(input[0]));
        for (int r = 1; r < rows; r++) {
            builder.append("\n");
            builder.append(String.valueOf(input[r]));
        }
        return builder.toString();
    }
}
