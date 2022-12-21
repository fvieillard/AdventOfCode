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
                monkey = new YellingMonkey(id, Integer.parseInt(matcher.group("value")));
            } else {
                monkey = new OperatingMonkey(
                        id,
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
        monkeys.replace("humn", new Human("humn"));
        OperatingMonkey root = (OperatingMonkey) monkeys.get("root");
        return new OperatingMonkey("root", root.monkey1, root.monkey2, "=").revert(null);
    }


    interface Monkey {
        String id();
        long getValue();
        long revert(Long neededValue);
    }

    record YellingMonkey(String id, int value) implements Monkey {
        public long getValue() {
            return value;
        }
        @Override
        public long revert(final Long neededValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public String toString() {
            return id + "(" + value + ")";
        }
    }

    record OperatingMonkey(String id, String monkey1, String monkey2, String operator) implements Monkey {
        @Override
        public long getValue() {
            Monkey m1 = monkeys.get(monkey1);
            long value1 = m1.getValue();
//            System.out.printf("Value returned for monkey %s : %s%n", m1, value1);
            Monkey m2 = monkeys.get(monkey2);
            long value2 = m2.getValue();
//            System.out.printf("Value returned for monkey %s : %s%n", m2, value2);
            return switch (operator) {
                case "+" -> value1 + value2;
                case "-" -> value1 - value2;
                case "*" -> value1 * value2;
                case "/" -> value1 / value2;
                default -> throw new UnsupportedOperationException(
                        String.format("Unknown operator '%s'", operator));
            };
        }

        @Override
        public long revert(final Long neededValue) {
            Monkey m1 = monkeys.get(monkey1);
            Monkey m2 = monkeys.get(monkey2);

            long constantValue;
            Monkey constantMonkey;
            Monkey remainingMonkey;
            try {
                constantValue = m1.getValue();
                constantMonkey = m1;
                remainingMonkey = m2;
            } catch (UnsupportedOperationException e) {
                constantValue = m2.getValue();
                constantMonkey = m2;
                remainingMonkey = m1;
            }
//            System.out.printf("Value returned for monkey %s : %s%n", m1, constantValue);
//            System.out.printf("Reverting %s = %s -  \"%s\" returned %s, ",
//                    this, neededValue, constantMonkey.id(), constantValue);

            long newNeededValue = switch (operator) {
                case "+" -> neededValue - constantValue;
                case "*" -> neededValue / constantValue;
                case "-" -> {
                    if (constantMonkey == m1)
                        yield constantValue - neededValue;
                    else
                        yield constantValue + neededValue;
                }
                case "/" -> {
                    if (constantMonkey == m1)
                        yield constantValue / neededValue;
                    else
                        yield constantValue * neededValue;
                }
                case "=" -> constantValue;
                default -> throw new UnsupportedOperationException(
                        String.format("Unknown operator '%s'", operator));
            };

//            System.out.printf("%s must then return %s%n", remainingMonkey.id(), newNeededValue);
            return remainingMonkey.revert(newNeededValue);
        }

        @Override
        public String toString() {
            return id + "(" + monkey1 + " " + operator + " " + monkey2 + ")";
        }
    }
    record Human(String id) implements Monkey {
        @Override
        public long getValue() {
            throw new UnsupportedOperationException();
        }

        @Override
        public long revert(final Long neededValue) {
            return neededValue;
        }
    }
}
