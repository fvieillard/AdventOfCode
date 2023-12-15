package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Day15 extends Day2023 {
    private static final Logger LOG = LogManager.getLogger();


    public Day15(InputStream input) {
        super(15, "Lens Library", input);
    }

    public static void main(String... args) {
        new Day15(Day15.class.getResourceAsStream("day_15.txt")).printDay();
    }

    @Override
    public Object getSolutionPart1() {
        return Arrays.stream(getInput().trim().split(","))
                .mapToInt(Day15::hashAlgorithm)
                .sum();
    }


    public static int hashAlgorithm(String input) {
        int currentValue = 0;
        for (int i = 0; i < input.length(); i++) {
            currentValue += input.charAt(i);
            currentValue = (currentValue * 17) % 256;
        }
        LOG.debug("{} -> {}", input, currentValue);
        return currentValue;
    }

    public Object getSolutionPart2() {
        List<List<Lens>> boxes = new ArrayList<>(256);
        for (int i = 0; i < 256; i++) {
            boxes.add(new ArrayList<>());
        }

        for (String step : getInput().trim().split(",")) {
            String step2[] = step.split("[-=]");
            String label = step2[0];
            int boxNumber = hashAlgorithm(label);
            List<Lens> box = boxes.get(boxNumber);
            if (step2.length == 1) {
                Lens lens = new Lens(label, 0);
                box.remove(lens);
                LOG.debug("step {} : removing lens from box {} => {}", step, boxNumber, box);
            } else {
                Lens lens = new Lens(label, Integer.valueOf(step2[1]));
                int idx = box.indexOf(lens);
                if (idx >= 0) {
                    box.remove(idx);
                    box.add(idx, lens);
                } else {
                    box.add(lens);
                }
                LOG.debug("step {} : Adding lens to box {} => {}", step, boxNumber, box);
            }
        }

        int result = 0;
        for (int b = 0; b < boxes.size(); b++) {
            List<Lens> box = boxes.get(b);
            for (int s = 0; s < box.size(); s++) {
                result += (b + 1) * (s + 1) * box.get(s).focal;
            }
        }
        return result;
    }

    record Lens(String label, int focal) {
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Lens && label.equals(((Lens) obj).label);
        }

        @Override
        public String toString() {
            return "[" + label + " " + focal + "]";
        }
    }
}
