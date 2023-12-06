package fr.fvieillard.adventofcode;

import java.io.IOException;
import java.io.InputStream;

public abstract class Day {
    private int year;
    private int day;
    private String title;
    private String input;


    public Day(int year, int day, String title, InputStream input) {
        this.year = year;
        this.day = day;
        this.title = title;
        try {
            this.input = new String(input.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Object getSolutionPart1();

    public abstract Object getSolutionPart2();

    public void printDay() {
        System.out.printf("--- %s, Day %s: %s ---%n", year, day, title);
        System.out.printf("Part 1: %s%n", getSolutionPart1());
        System.out.printf("Part 2: %s%n", getSolutionPart2());
    }

    protected String getInput() {
        return input;
    }
}
