package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day11 extends Day2022 {

    private static final Pattern MONKEY = Pattern.compile("""
Monkey (?<id>\\d+):
  Starting items: (?<items>.*)
  Operation: new = old (?<operator>[+*]) (?<operand>\\d+|old)
  Test: divisible by (?<test>\\d+)
    If true: throw to monkey (?<ifTrue>\\d+)
    If false: throw to monkey (?<IfFalse>\\d+)""");

    List<Monkey> monkeys;

    public Day11(InputStream input) {
        super(11, "Monkey in the Middle", input);
    }

    public static void main(String... args) {
        new Day11(Day11.class.getResourceAsStream("day_11.txt")).printDay();
    }

    protected List<Monkey> parseMonkeylist() {
        List<Monkey> result = new ArrayList<>();
        Matcher matcher = MONKEY.matcher(getInput());
        while(matcher.find()) {
//            System.out.printf("Monkey detected:%n%s%n%n", matcher.group(0));
            int monkeyId = Integer.parseInt(matcher.group("id"));
            Monkey monkey = new Monkey(
                    monkeyId,
                    Arrays.stream(matcher.group("items").split(", "))
                            .mapToLong(Long::parseLong)
                            .collect(ArrayDeque::new, Deque::add, Deque::addAll),
                    switch (matcher.group("operator")) {
                        case "+" -> Operator.ADD;
                        case "*" -> {
                            switch (matcher.group("operand")) {
                                case "old": yield Operator.SQUARE;
                                default: yield Operator.MULTIPLY;
                            }
                        }
                        default -> throw new IllegalArgumentException("operation not supported");
                    },
                    switch (matcher.group("operand")) {
                        case "old" -> 0;
                        default -> Integer.parseInt(matcher.group("operand"));
                    },
                    Integer.parseInt(matcher.group("test")),
                    Integer.parseInt(matcher.group("ifTrue")),
                    Integer.parseInt(matcher.group("IfFalse"))
            );
//            System.out.printf("%s%n", monkey);
            result.add(monkeyId, monkey);
        }
        return result;
    }

    long simulateRounds(int nbRounds, int divideWorryAfterInspection) {
        monkeys = parseMonkeylist();
        Monkey.divideWorryAfterInspection = divideWorryAfterInspection;
        for (int i=1; i<=nbRounds; i++) {
            monkeys.forEach(Monkey::inspectAll);

//            if (i<=20 || i%1000==0) {
//                System.out.printf("=== Round %s:%n", i);
//                monkeys.forEach(monkey -> {
//                    System.out.printf("Monkey %s: %s - %s%n", monkey.id, monkey.items, monkey.activity);
//                });
//            }
        }
//        System.out.printf("%n%nTotal activity: %n");
//        monkeys.forEach(monkey -> {
//            System.out.printf("Monkey %s: %s%n", monkey.id, monkey.activity);
//        });
        long[] activity = monkeys.stream()
                .map(monkey -> monkey.activity)
                .sorted(Comparator.reverseOrder())
                .limit(2).mapToLong(i -> (long)i).toArray();
        return activity[0] * activity[1];
    }

    @Override
    public Object getSolutionPart1() {
        return simulateRounds(20, 3);
    }

    @Override
    public Object getSolutionPart2() {
        return simulateRounds(10000, 1);
    }


    enum Operator{ADD, MULTIPLY, SQUARE}

    class Monkey{
        int id;
        Deque<Long> items;
        Operator operator;
        int operand;
        int testDivisibleBy;
        int monkeyIfTrue;
        int monkeyIfFalse;
        // Counter for each item inspected
        int activity;
        static int divideWorryAfterInspection;
        static int reduceFactor;

        private Monkey(final int id, final Deque<Long> items, final Operator operator, final int operand, final int testDivisibleBy, final int monkeyIfTrue, final int monkeyIfFalse) {
            this.id = id;
            this.items = items;
            this.operator = operator;
            this.operand = operand;
            this.testDivisibleBy = testDivisibleBy;
            this.monkeyIfTrue = monkeyIfTrue;
            this.monkeyIfFalse = monkeyIfFalse;
        }

        private void inspectAll() {
            reduceFactor = monkeys.stream()
                    .mapToInt(value -> value.testDivisibleBy)
                    .reduce((left, right) -> left * right).getAsInt();
            while (items.size() > 0) {
                inspectAndMove(items.removeFirst());
            }
        }
        private void inspectAndMove(long worryLevel) {
//            System.out.printf("Monkey %s inspects item with a worry level of %s.%n", id, worryLevel);

            // Apply Operator
            switch (operator) {
                case ADD -> {
//                    System.out.printf("  Worry level increases by %s to %s.%n", operand, worryLevel);
                    worryLevel += operand;
                }
                case MULTIPLY -> {
                    worryLevel *= operand;
//                    System.out.printf("  Worry level is multiplied by %s to %s.%n", operand, worryLevel);
                }
                case SQUARE -> {
                    worryLevel *= worryLevel;
//                    System.out.printf("  Worry level is multiplied by itself to %s.%n", worryLevel);
                }
            }

            worryLevel = worryLevel / divideWorryAfterInspection;
//            System.out.printf("  Monkey gets bored with item. Worry level is divided by 3 to %s.%n", worryLevel);


            // Test level of worry
            int targetMonkey;
            if (worryLevel %testDivisibleBy == 0) {
//                System.out.printf("  Current worry level is divisible by %s.%n", testDivisibleBy);
                targetMonkey = monkeyIfTrue;
            } else {
//                System.out.printf("  Current worry level is not divisible by %s.%n", testDivisibleBy);
                targetMonkey = monkeyIfFalse;
            }

            // Throw item
            // The numbers are rapidly becoming huge
            // so the program cannot execute in a reasonable time.
            // Since we don't need the real numbers but only a number with the same properties of divisibily
            // by each monkey, we can keep the remainder of the division by the product of each monkey "divisor"
            monkeys.get(targetMonkey).items.add(worryLevel % reduceFactor);
//            System.out.printf("  Item with worry level %s is thrown to monkey %s.%n", worryLevel, targetMonkey);

            // Increment the activity counter
            activity++;
        }

        @Override
        public String
        toString() {
            return "Monkey{" +
                    "id=" + id +
                    ", items=" + items +
                    ", operator=" + operator +
                    ", operand=" + operand +
                    ", testDivisibleBy=" + testDivisibleBy +
                    ", monkeyIfTrue=" + monkeyIfTrue +
                    ", monkeyIfFalse=" + monkeyIfFalse +
                    '}';
        }
    }
}
