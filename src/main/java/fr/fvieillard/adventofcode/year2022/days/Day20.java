package fr.fvieillard.adventofcode.year2022.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import fr.fvieillard.adventofcode.common.CircularList;
import fr.fvieillard.adventofcode.year2022.Day2022;


public class Day20 extends Day2022 {
    public Day20(InputStream input) {
        super(20, "Grove Positioning System", input);
    }

    public static void main(String... args) {
        new Day20(Day20.class.getResourceAsStream("day_20.txt")).printDay();
    }


    private long result(long decryptionKey, int nbRounds) {
        int i = 0;
        Element zero = null;
        List<Element> list = new ArrayList<>();
        for (String s:getInput().split("\n")) {
            Element e = new Element(i++, Integer.parseInt(s) * decryptionKey);
            if (e.value == 0) {
                zero = e;
            }
            list.add(e);
        }
        CircularList<Element> circularList = new CircularList<>(list);

//        System.out.printf("Initial list : %s%n", circularList);

        for (int round = 1; round <= nbRounds; round++){
            list.forEach(e -> {
                circularList.move(e, e.value);
//            System.out.printf("Moving %5s  -  Circular List:%s%n", i, circularList);
            });
//            System.out.printf("List after round %2s : %s%n", round, circularList);
        }

        int idx0 = circularList.indexOf(zero);
//        System.out.printf("O is at position %s%n", idx0);
        return IntStream.of(1000, 2000, 3000)
                .map(n -> idx0 + n)
                .mapToObj(circularList::get)
                .mapToLong(Element::value)
                .sum();
    }

    @Override
    public Object getSolutionPart1() {
        return result(1, 1);
    }

    @Override
    public Object getSolutionPart2() {
        return result(811589153, 10);
    }


    record Element(int originalOrder, long value){
        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
