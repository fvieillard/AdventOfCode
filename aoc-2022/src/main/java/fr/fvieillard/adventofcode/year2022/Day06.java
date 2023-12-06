package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.stream.Collectors;

public class Day06 extends Day2022 {

    public Day06(InputStream input) {
        super(6, "Tuning Trouble", input);
    }

    public static void main(String... args) {
        new Day06(Day06.class.getResourceAsStream("day_06.txt")).printDay();
    }

    private Integer findPacket(int length) {
        String in = getInput();
//        System.out.printf("Input: %s", in);

        for (int i = length; i < in.length(); i++) {
            String sub = in.substring(i - length, i);
            int numberOfChars = sub.chars().mapToObj(value -> (char)value).collect(Collectors.toSet()).size();
//            System.out.printf("Index %s, Analyzing \"%s\" - size: %s%n", i, sub, numberOfChars);
            if (numberOfChars >= length) return i;
        }
        return null;
    }

    @Override
    public Object getSolutionPart1() {
        return findPacket(4);
    }

    @Override
    public Object getSolutionPart2() {
        return findPacket(14);
    }

}
