package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day10 extends Day2022 {

    private List<Integer> registerValues = new ArrayList<>();

    public Day10(InputStream input) {
        super(10, "Cathode-Ray Tube", input);
    }

    public static void main(String... args) {
        new Day10(Day10.class.getResourceAsStream("day_10.txt")).printDay();
    }

    @Override
    protected void processInput() {
        Scanner scanner = new Scanner(getInput());
        Integer X = 1;
        while (scanner.hasNext()) {
            switch (scanner.next()) {
                case "noop" -> {
                    // noop takes 1 cycle and does nothing
                    registerValues.add(X);
                }
                case "addx" -> {
                    // addx takes 2 cycles and at the end of the 2 cycles, register is updated
                    registerValues.add(X);
                    registerValues.add(X);
                    X += scanner.nextInt();
                }
            }
        }

//        System.out.printf("Register value through cycles:%n%s%n", registerValues);
    }

    @Override
    public Object getSolutionPart1() {
        int accumulatedStrength = 0;
        for (int i = 20; i < registerValues.size(); i += 40) {
            Integer register = registerValues.get(i - 1);
            int strength = register * i;
//            System.out.printf("Register value at %1$sth cycle: %2$s -> strength : %1$s * %2$s = %3$s%n", i, register, strength);
            accumulatedStrength += strength;
        }
        return accumulatedStrength;
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }
}
