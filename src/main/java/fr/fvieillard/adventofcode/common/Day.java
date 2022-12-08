package fr.fvieillard.adventofcode.common;

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

    /**
     * This method should be overrided if the input needs processing that is common to part one and part two.
     */
    protected void processInput() {
    }

    public abstract Object getSolutionPart1();

    public abstract Object getSolutionPart2();

    public void printDay() {
        System.out.printf("--- %s, Day %s: %s ---%n", year, day, title);
        processInput();
        System.out.printf("Part 1: %s%n", getSolutionPart1());
        System.out.printf("Part 2: %s%n", getSolutionPart2());
    }

    protected String getInput() {
        return input;
    }
}
