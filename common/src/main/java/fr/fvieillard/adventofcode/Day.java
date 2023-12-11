package fr.fvieillard.adventofcode;

import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Day {
    protected final Logger LOG;

    private int year;
    private int day;
    private String title;
    private String input;


    public Day(int year, int day, String title, InputStream input) {
        this.LOG = LogManager.getLogger(this.getClass());
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
