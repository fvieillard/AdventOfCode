package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.Scanner;

public class Day04 extends Day2022 {

    public Day04(InputStream input) {
        super(4, "Camp Cleanup", input);
    }

    public static void main(String... args) {
        new Day04(Day04.class.getResourceAsStream("day_04.txt")).printDay();
    }


    @Override
    public Object getSolutionPart1() {
        int counter = 0;
        Scanner in = new Scanner(getInput()).useDelimiter("[\\n,-]");

        while (in.hasNext()) {
            int[] range1 = {in.nextInt(), in.nextInt()};
            int[] range2 = {in.nextInt(), in.nextInt()};

            boolean firstInSecond = range1[0] >= range2[0] && range1[1] <= range2[1];
            boolean secondInFirst = range2[0] >= range1[0] && range2[1] <= range1[1];

//            System.out.printf("range1: %s, range2: %s - 1 in 2: %s, 2 in 1: %s%n",
//                    Arrays.toString(range1), Arrays.toString(range2), firstInSecond, secondInFirst);

            if (firstInSecond || secondInFirst) counter++;
        }
        return counter;
    }

    @Override
    public Object getSolutionPart2() {
        int counter = 0;
        Scanner in = new Scanner(getInput()).useDelimiter("[\\n,-]");

        while (in.hasNext()) {
            int[] range1 = {in.nextInt(), in.nextInt()};
            int[] range2 = {in.nextInt(), in.nextInt()};

            boolean overlap = !(range1[1] < range2[0] || range1[0] > range2[1]);
//            System.out.printf("range1: %s, range2: %s - overlap: %s%n",
//                    Arrays.toString(range1), Arrays.toString(range2), overlap);

            if (overlap) counter++;
        }
        return counter;
    }

}
