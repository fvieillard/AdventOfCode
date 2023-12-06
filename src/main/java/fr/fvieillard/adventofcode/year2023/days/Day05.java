package fr.fvieillard.adventofcode.year2023.days;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import fr.fvieillard.adventofcode.year2023.Day2023;

public class Day05 extends Day2023 {

    private SortedSet<Range> seedToSoil = new TreeSet<>();
    private SortedSet<Range> soilToFertilizer = new TreeSet<>();
    private SortedSet<Range> fertilizerToWater = new TreeSet<>();
    private SortedSet<Range> waterToLight = new TreeSet<>();
    private SortedSet<Range> lightToTemperature = new TreeSet<>();
    private SortedSet<Range> temperatureToHumidity = new TreeSet<>();
    private SortedSet<Range> humidityToLocation = new TreeSet<>();
    private List<Long> seeds = new ArrayList<>();

    public Day05(InputStream input) {
        super(5, "If You Give A Seed A Fertilizer", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day05(Day05.class.getResourceAsStream("day_05.txt")).printDay();
    }

    void parseInput() {
        SortedSet<Range> currentMap = new TreeSet<>();
        for (String line : getInput().split("\n")) {
            if (line.startsWith("seeds: ")) {
                Arrays.stream(line.substring(7).split(" "))
                        .mapToLong(Long::valueOf)
                        .forEach(seeds::add);
            } else {
                switch (line) {
                    case "seed-to-soil map:":
                        currentMap = seedToSoil;
                        break;
                    case "soil-to-fertilizer map:":
                        currentMap = soilToFertilizer;
                        break;
                    case "fertilizer-to-water map:":
                        currentMap = fertilizerToWater;
                        break;
                    case "water-to-light map:":
                        currentMap = waterToLight;
                        break;
                    case "light-to-temperature map:":
                        currentMap = lightToTemperature;
                        break;
                    case "temperature-to-humidity map:":
                        currentMap = temperatureToHumidity;
                        break;
                    case "humidity-to-location map:":
                        currentMap = humidityToLocation;
                        break;
                    case "":
                        break;
                    default:
                        currentMap.add(Range.fromString(line));
                        break;
                }
            }
        }
    }

    @Override
    public Object getSolutionPart1() {
        return seeds.stream()
                .mapToLong(value -> mapSeedToLocation(value))
                .min().getAsLong();
    }

    @Override
    public Object getSolutionPart2() {
        List<Long> realseeds = new ArrayList<>();
        for (int i=0; i<seeds.size(); i=i+2) {
            for (long j=0; j<seeds.get(i+1); j++) {
                realseeds.add(seeds.get(i)+j);
            }
        }
        return realseeds.stream()
                .mapToLong(value -> mapSeedToLocation(value))
                .min().getAsLong();
    }


    Long mapSeedToLocation(Long seed) {
        System.out.print("Seed " + seed);
        Long soil = map(seed, seedToSoil);
        System.out.print(", soil " + soil);
        Long fertilizer = map(soil, soilToFertilizer);
        System.out.print(", fertilizer " + fertilizer);
        Long water = map(fertilizer, fertilizerToWater);
        System.out.print(", water " + water);
        Long light = map(water, waterToLight);
        System.out.print(", light " + light);
        Long temperature = map(light, lightToTemperature);
        System.out.print(", temperature " + temperature);
        Long humidity = map(temperature, temperatureToHumidity);
        System.out.print(", humidity " + humidity);
        Long location = map(humidity, humidityToLocation);
        System.out.println(", location " + location);
        return location;
    }

    static Long map(Long value, SortedSet<Range> map) {
        for (Range range : map) {
            if (range.contains(value)) {
                return range.mapToDestination(value);
            }
        }
        return value;
    }

    record Range(Long destinationStart, Long sourceStart,
                 Long rangeLength) implements Comparable<Range> {
        static Range fromString(String input) {
            String[] in = input.split(" ");
            return new Range(Long.valueOf(in[0]), Long.valueOf(in[1]), Long.valueOf(in[2]));
        }

        boolean contains(Long value) {
            return value >= sourceStart && value < (sourceStart + rangeLength);
        }

        Long mapToDestination(Long value) {
            if (!contains(value)) {
                throw new IllegalArgumentException("Not within range");
            }
            return value - sourceStart + destinationStart;
        }

        @Override
        public int compareTo(final Range o) {
            return sourceStart.compareTo(o.sourceStart);
        }
    }

}
