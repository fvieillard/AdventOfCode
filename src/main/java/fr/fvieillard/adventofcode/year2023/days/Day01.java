package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day01 extends Day2023  {
    public Day01(InputStream input) {
        super(1, "Trebuchet?!", input);
    }

    public static void main(String... args) {
        new Day01(Day01.class.getResourceAsStream("day_01.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return getInput()
                .replaceAll("[^\\d\\r\\n]","")
                .lines()
                .filter(Predicate.not(String::isBlank))
                .mapToInt(value -> {
                    return Integer.valueOf(value.substring(0, 1) + value.substring(value.length() - 1));
                })
                .sum();
    }

    @Override
    public Object getSolutionPart2() {
        Pattern firstDigit = Pattern.compile("(one|two|three|four|five|six|seven|eight|nine|\\d).*");
        Pattern lastDigit = Pattern.compile(".*(one|two|three|four|five|six|seven|eight|nine|\\d)");
        return getInput()
                .lines()
                .mapToInt(value -> {
                    return Integer.valueOf(extractDigit(value, firstDigit) + extractDigit(value, lastDigit));
                })
                .sum();
    }

    private String extractDigit(String input, Pattern pattern) {
        Matcher match = pattern.matcher(input);
        match.find(0);
        return switch (match.group(1)) {
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            default -> match.group(1);
        };
    }
}
