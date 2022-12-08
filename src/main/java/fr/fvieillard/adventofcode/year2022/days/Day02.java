package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.Scanner;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day02 extends Day2022 {

    public Day02(InputStream input) {
        super(2, "Rock Paper Scissors", input);
    }

    public static void main(String... args) {
        new Day02(Day02.class.getResourceAsStream("day_02.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        int score = 0;
        Scanner inputScanner = new Scanner(getInput());
        while (inputScanner.hasNext()) {
            String his = inputScanner.next();
            String mine = inputScanner.next();
            if ("X".equals(mine)) {     // I played Rock
                score += 1;
                switch (his) {
                    case "A" -> score += 3; // He played Rock: DRAW
                    case "B" -> score += 0; // He played Paper: LOSE
                    case "C" -> score += 6; // He played Scissor: WIN
                }
            } else if ("Y".equals(mine)) {  // I played Paper
                score += 2;
                switch (his) {
                    case "A" -> score += 6; // He played Rock: WIN
                    case "B" -> score += 3; // He played Paper: DRAW
                    case "C" -> score += 0; // He played Scissor: LOSE
                }
            } else if ("Z".equals(mine)) {  // I played Scissor
                score += 3;
                switch (his) {
                    case "A" -> score += 0; // He played Rock: LOSE
                    case "B" -> score += 6; // He played Paper: WIN
                    case "C" -> score += 3; // He played Scissor: DRAW
                }
            }
        }
        return score;
    }

    @Override
    public Object getSolutionPart2() {
        int score = 0;
        Scanner inputScanner = new Scanner(getInput());
        while (inputScanner.hasNext()) {
            String his = inputScanner.next();
            String mine = inputScanner.next();
            if ("X".equals(mine)) {     // Need a lose
                score += 0;
                switch (his) {
                    case "A" -> score += 3; // He played Rock -> I play Scissor
                    case "B" -> score += 1; // He played Paper -> I play Rock
                    case "C" -> score += 2; // He played Scissor -> I play Paper
                }
            } else if ("Y".equals(mine)) {  // Need a draw
                score += 3;
                switch (his) {
                    case "A" -> score += 1; // He played Rock -> I play Rock
                    case "B" -> score += 2; // He played Paper -> I play Paper
                    case "C" -> score += 3; // He played Scissor -> I play Scissor
                }
            } else if ("Z".equals(mine)) {  // Need a win
                score += 6;
                switch (his) {
                    case "A" -> score += 2; // He played Rock -> I play Paper
                    case "B" -> score += 3; // He played Paper -> I play Scissor
                    case "C" -> score += 1; // He played Scissor -> I play Rock
                }
            }
        }
        return score;
    }
}
