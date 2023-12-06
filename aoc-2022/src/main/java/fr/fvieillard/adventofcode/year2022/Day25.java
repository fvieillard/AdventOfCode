package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;


public class Day25 extends Day2022 {
    public Day25(InputStream input) {
        super(25, "Full of Hot Air", input);
    }

    public static void main(String... args) {
        new Day25(Day25.class.getResourceAsStream("day_25.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        //TODO: would also be interesting to implement the addition in SNAFU.
        return decimalToSnafu(
                getInput().lines()
                .mapToLong(Day25::snafuToDecimal)
                .sum()
        );
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    static long snafuToDecimal(String number) {
        int power = number.length() - 1;

        long result = 0;
        for (int i=0; i <= power; i++) {
            result += Math.pow(5, power-i) * switch (number.charAt(i)) {
                case '2' -> 2;
                case '1' -> 1;
                case '0' -> 0;
                case '-' -> -1;
                case '=' -> -2;
                default -> throw new IllegalArgumentException();
            };
        }
        return result;
    }

    static String decimalToSnafu(long number) {
        String result = "";
        long num = number;
        long currentRank = 1;
        long nextRank;
        int carry = 0;
        while (num > 0 || carry > 0) {
            nextRank = 5 * currentRank;
            long remainder = num % nextRank;
            char digit = switch ((int)(remainder / currentRank + carry)) {
                case 0 -> {carry=0; yield '0';}
                case 1 -> {carry=0; yield '1';}
                case 2 -> {carry=0; yield '2';}
                case 3 -> {carry=1; yield '=';}
                case 4 -> {carry=1; yield '-';}
                case 5 -> {carry=1; yield '0';}
                default -> throw new IllegalArgumentException();
            };
            num = num - remainder;
            currentRank = nextRank;
            result = digit + result;
        }
        return result;
    }

}
