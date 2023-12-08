package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.common.CircularList;
import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day08 extends Day2023 {

    Map<String, String[]> nodes = new HashMap<>();
    CircularList<String> instructions;

    public Day08(InputStream input) {
        super(8, "Haunted Wasteland", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day08(Day08.class.getResourceAsStream("day_08.txt")).printDay();
    }


    protected void parseInput() {
        final Pattern nodePattern = Pattern.compile("(\\w{3}) = \\((\\w{3}), (\\w{3})\\)");
        instructions = new CircularList<>(getInput().lines().findFirst().get().chars().mapToObj(value -> "" + (char) value).toList());
        getInput().lines().skip(2).forEachOrdered(s -> {
            Matcher match = nodePattern.matcher(s);
            if (match.matches()) {
                nodes.put(match.group(1), new String[]{match.group(2), match.group(3)});
            }
        });

        System.err.println(instructions);
        System.err.println(nodes);
    }

    @Override
    public Object getSolutionPart1() {
        return getNumberOfSteps("AAA", s -> s.equals("ZZZ"));
    }

    @Override
    public Object getSolutionPart2() {
        List<String> currentNodes = nodes.keySet().parallelStream().filter(s -> s.charAt(2) == 'A').toList();
        System.err.println("Starting points: " + currentNodes);

        for (String start : currentNodes) {
            int steps = getNumberOfSteps(start, s -> s.charAt(2) == 'Z');
            System.err.println(String.format("for start point %s, we reach a solution in %s steps", start, steps));
        }

        //TODO: implement LCM search for all results
        return null;
    }


    public int getNumberOfSteps(String startnode, Predicate<String> stopCondition) {
        String currentNode = startnode;
        int instructionIndex = 0;
        while (!stopCondition.test(currentNode)) {
            String[] destinationNodes = nodes.get(currentNode);
            currentNode = switch (instructions.get(instructionIndex)) {
                case "L" -> destinationNodes[0];
                case "R" -> destinationNodes[1];
                default -> throw new IllegalArgumentException();
            };
            instructionIndex++;
        }

        return instructionIndex;
    }
}
