package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import fr.fvieillard.adventofcode.year2022.Day2022;

public class Day05 extends Day2022 {

    private static final Pattern INSTRUCTION_PATTERN = Pattern.compile("move (\\d*) from (\\d*) to (\\d*)");
    private List<Deque<Character>> map;

    public Day05(InputStream input) {
        super(5, "Supply Stacks", input);
    }

    public static void main(String... args) {
        new Day05(Day05.class.getResourceAsStream("day_05.txt")).printDay();
    }

    /**
     * Parses Map of crates and return a Scanner with the instructions remaining
     * @return Scanner with instructions
     */
    protected Scanner parseInput() {

        Scanner inputScanner = new Scanner(getInput());
        // Read Map of crates
        int colIndex = -1;
        map = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            String line = inputScanner.nextLine();
            // break loop when end of map is reached
            if (line.startsWith(" 1")) break;
//            System.out.printf("%nParsing line: %s%n", line);
            for (int i = 0; i <= line.length()/4; i++) {
                char crate = line.charAt(4*i+1);
                if (i > colIndex) {
                    map.add(new ArrayDeque<>());
                    colIndex = i;
                }
                if (' ' != crate) {
                    map.get(i).addLast(crate);
                }
            }
        }
//        System.out.printf("map of crates: %s%n%n", map);

        // Instructions are left
        return inputScanner;
    }

    @Override
    public Object getSolutionPart1() {
        Scanner instructions = parseInput();
        instructions.useDelimiter("\n");
        instructions.forEachRemaining(line -> {
            Matcher matcher = INSTRUCTION_PATTERN.matcher(line);
            matcher.matches();
            int num = Integer.parseInt(matcher.group(1));
            int from = Integer.parseInt(matcher.group(2));
            int to = Integer.parseInt(matcher.group(3));

            Deque<Character> source = map.get(from - 1);
            Deque<Character> target = map.get(to - 1);
            for (int i = 0; i < num; i++) {
                target.push(source.pop());
            }
        });
//        System.out.printf("Result Map: %s%n", map);
        return map.stream().map(d -> d.peek().toString()).collect(Collectors.joining());
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }

}
