package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day21 extends Day2022 {

    private static final Pattern MONKEY = Pattern.compile("""
            (?<id>[a-z]{4}): ((?<value>\\d+)|(?<monkey1>[a-z]{4}) (?<operator>[+\\-*/]) (?<monkey2>[a-z]{4}))""");

    private static Map<String, Monkey> monkeys;

    public Day21(InputStream input) {
        super(21, "Monkey Math", input);
    }

    public static void main(String... args) {
        new Day21(Day21.class.getResourceAsStream("day_21.txt")).printDay();
    }

    @Override
    protected void processInput() {
        monkeys = new HashMap<>();
        Matcher matcher = MONKEY.matcher(getInput());
        while (matcher.find()) {
            String id = matcher.group("id");
            Monkey monkey;
            if (matcher.group("value") != null) {
                monkey = new YellingMonkey(Integer.parseInt(matcher.group("value")));
            } else {
                monkey = new OperatingMonkey(
                        matcher.group("monkey1"),
                        matcher.group("monkey2"),
                        matcher.group("operator"));
            }
            monkeys.put(id, monkey);
        }
    }

    @Override
    public Object getSolutionPart1() {
        return monkeys.get("root").getValue();
    }

    @Override
    public Object getSolutionPart2() {
        return null;
    }


    interface Monkey {
        long getValue();
    }

    record YellingMonkey(int value) implements Monkey {
        public long getValue() {
            return value;
        }
    }

    record OperatingMonkey(String monkey1, String monkey2, String operator) implements Monkey {
        @Override
        public long getValue() {
            Monkey m1 = monkeys.get(monkey1);
            Monkey m2 = monkeys.get(monkey2);
            return switch (operator) {
                case "+" -> m1.getValue() + m2.getValue();
                case "-" -> m1.getValue() - m2.getValue();
                case "*" -> m1.getValue() * m2.getValue();
                case "/" -> m1.getValue() / m2.getValue();
                default -> throw new RuntimeException(
                        String.format("Unsupported operation '%s'", operator));
            };
        }
    }
}
