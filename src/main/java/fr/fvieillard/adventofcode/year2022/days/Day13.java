package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day13 extends Day2022 {
    public Day13(InputStream input) {
        super(13, "Distress Signal", input);
    }

    public static void main(String... args) {
        new Day13(Day13.class.getResourceAsStream("day_13.txt")).printDay();
    }


    @Override
    public Object getSolutionPart1() {
        int result = 0;
        int i = 0;
        for (String pair:getInput().split("\n\n")) {
            i++;
            String left = pair.split("\n")[0];
            String right = pair.split("\n")[1];

//            System.out.printf("===  Left: %s, Right: %s%n", left, right);

            if (Element.parse(left).compareTo(Element.parse(right)) <=0 ) {
//                System.out.printf("Left < Right  --> right order !%n%n");
                result += i;
            }
//            else {
//                System.out.printf("Left > Right  --> wrong order !%n%n");
//            }
        }
        return result;
    }

    @Override
    public Object getSolutionPart2() {
        List<Element> list = getInput().lines()
                .filter(Predicate.not(String::isBlank))
                .map(Element::parse)
                .collect(Collectors.toList());
        Element divider1 = Element.parse("[[2]]");
        list.add(divider1);
        Element divider2 = Element.parse("[[6]]");
        list.add(divider2);

        Collections.sort(list);
//        System.out.printf("Sorted List: %n");
//        list.forEach(System.out::println);
        int idx1 = list.indexOf(divider1) + 1;
        int idx2 = list.indexOf(divider2) + 1;
//        System.out.printf("Index of divider %s: %s%n", divider1, idx1);
//        System.out.printf("Index of divider %s: %s%n", divider2, idx2);
        return idx1 * idx2;
    }


    static abstract sealed class Element
        implements Comparable<Element>
            permits IntegerElement, ListElement {

        static private Element parse(String in) {
            ListElement result = null;
            Stack<List<Element>> stack = new Stack<>();
            // Will hold the remaining of the string to be parsed.
            String tmp = in;
            // Will hold the digits when no separator is encountered.
            String currentNum = "";
//            System.out.printf("%n%n=== Start parsing %s%n", in);
            while (!tmp.isEmpty()) {
//                System.out.printf("%s  -->  ", tmp);

                // Extract first char and remove it from the working copy of the string.
                char currentChar = tmp.charAt(0);
                tmp = tmp.substring(1);

                switch (currentChar) {
                    case '[', ']', ',' -> {
                        if (!currentNum.isEmpty()) {
//                            System.out.printf("Separator, adding current IntegerElement (%s). ", currentNum);
                            stack.peek().add(new IntegerElement(Integer.parseInt(currentNum)));
                            currentNum = "";
                        }
                    }
                }
                switch (currentChar) {
                    case '[' -> {
                        stack.push(new ArrayList<>());
//                        System.out.printf("Begin new list%n");
                    }
                    case ']' -> {
                        ListElement completedList = new ListElement(stack.pop());
                        result = completedList;
                        if (!stack.isEmpty()) stack.peek().add(completedList);
//                        System.out.printf("List completed, content: %s%n", completedList);
                    }
                    case ',' -> {
//                        System.out.printf("Sep, juste consume...%n");
                    }
                    default -> {
                        currentNum = currentNum + currentChar;
//                        System.out.printf("Digit, add to temp value, now: %s%n", currentNum);
                    }
                }
            }
//            System.out.printf("%n--- Finished parsing !! %n" +
//                              "   -> Result: %s%n", result);
            return result;
        }
    }
    static final class IntegerElement extends Element {
        int value;
        IntegerElement(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public int compareTo(final Element o) {
            if (o instanceof IntegerElement ie) {
                return value - ie.value;
            } else if (o instanceof  ListElement le) {
                return new ListElement(new Element[]{this}).compareTo(le);
            }
            throw new IllegalStateException();
        }
    }
    static final class ListElement extends Element {
        Element[] elems;
        ListElement(Element[] elems) {
            this.elems = elems;
        }
        ListElement(List<Element> elems) {
            this.elems = elems.toArray(new Element[]{});
        }

        @Override
        public String toString() {
            return Arrays.toString(elems);
        }

        @Override
        public int compareTo(final Element o) {
            if (o instanceof IntegerElement ie) {
                return - ie.compareTo(this);
            } else if (o instanceof  ListElement le) {
                return Arrays.compare(elems, le.elems);
            }
            throw new IllegalStateException();
        }
    }
}
