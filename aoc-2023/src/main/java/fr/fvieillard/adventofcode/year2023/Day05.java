package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day05 extends Day2023 {

    private SortedSet<MappingRange> seedToSoil = new TreeSet<>();
    private SortedSet<MappingRange> soilToFertilizer = new TreeSet<>();
    private SortedSet<MappingRange> fertilizerToWater = new TreeSet<>();
    private SortedSet<MappingRange> waterToLight = new TreeSet<>();
    private SortedSet<MappingRange> lightToTemperature = new TreeSet<>();
    private SortedSet<MappingRange> temperatureToHumidity = new TreeSet<>();
    private SortedSet<MappingRange> humidityToLocation = new TreeSet<>();
    private List<Long> seeds = new ArrayList<>();

    public Day05(InputStream input) {
        super(5, "If You Give A Seed A Fertilizer", input);
        parseInput();
    }

    public static void main(String... args) {
        new Day05(Day05.class.getResourceAsStream("day_05.txt")).printDay();
    }

    protected void parseInput() {
        SortedSet<MappingRange> currentMap = new TreeSet<>();
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
                        currentMap.add(MappingRange.fromString(line));
                        break;
                }
            }
        }
    }

    @Override
    public Object getSolutionPart1() {
        return mapSeedToLocation(
                seeds.stream().map(aLong -> new Range(aLong, 1L)).toList()
        ).stream().mapToLong(Range::start).min().getAsLong();
    }

    @Override
    public Object getSolutionPart2() {
        Collection<Range> realSeeds = new TreeSet<>();
        for (int i = 0; i < seeds.size(); i = i + 2) {
            realSeeds.add(new Range(seeds.get(i), seeds.get(i + 1)));
        }
        return mapSeedToLocation(realSeeds).stream().mapToLong(Range::start).min().getAsLong();
    }


    Collection<Range> mapSeedToLocation(Collection<Range> seed) {
//        System.out.println("Seed " + seed);
        Collection<Range> soil = map(seed, seedToSoil);
//        System.out.println(", soil " + soil);
        Collection<Range> fertilizer = map(soil, soilToFertilizer);
//        System.out.println(", fertilizer " + fertilizer);
        Collection<Range> water = map(fertilizer, fertilizerToWater);
//        System.out.println(", water " + water);
        Collection<Range> light = map(water, waterToLight);
//        System.out.println(", light " + light);
        Collection<Range> temperature = map(light, lightToTemperature);
//        System.out.println(", temperature " + temperature);
        Collection<Range> humidity = map(temperature, temperatureToHumidity);
//        System.out.println(", humidity " + humidity);
        Collection<Range> location = map(humidity, humidityToLocation);
//        System.out.println(", location " + location);
        return location;
    }

    static Collection<Range> map(Collection<Range> value, Collection<MappingRange> map) {
        Collection<Range> nonMappedRanges = new ArrayList<>(value);
        Collection<Range> mappedRanges = new ArrayList<>();
        for (MappingRange mappingRange : map) {
            Collection<Range> intermediate = new ArrayList<>(nonMappedRanges);
            for (Range input : nonMappedRanges) {
                 for (Range r:mappingRange.range().intersect(input)) {
                    if (mappingRange.range.fullyContains(r)) {
                        intermediate.remove(input);
                        mappedRanges.add(mappingRange.mapToDestination(r));
                    } else {
                        intermediate.add(r);
                    }
                 }
            }
            nonMappedRanges = intermediate;
        }

        nonMappedRanges.addAll(mappedRanges);
        return nonMappedRanges;
    }

    record Range(Long start, Long length) implements Comparable<Range> {

        @Override
        public String toString() {
            return String.format("[%s -> %s]", start(), end());
        }

        Long end() {
            return start + length - 1;
        }

        @Override
        public int compareTo(final Range o) {
            return start.compareTo(o.start);
        }

        boolean contains(Range value) {
            return contains(value.start()) || contains(value.end());
        }

        boolean fullyContains(Range value) {
            return contains(value.start()) && contains(value.end());
        }

        boolean contains(Long value) {
            return value >= start() && value <= end();
        }

        static Range fromBoundaries(long start, long end) {
            return new Range(start, end - start);
        }

        Collection<Range> intersect(Range value) {
            Collection<Range> result = new TreeSet<>();
            if (contains(value)) {
                if (value.start() < start()) {
                    result.add(new Range(value.start, start() - value.start()));
                }
                if (value.end() > end()) {
                    result.add(new Range(end() + 1, value.end() - end()));
                }
                result.add(Range.fromBoundaries(Math.max(value.start(), start()), Math.min(value.end(), end()) + 1));
            }
//            System.out.println(String.format("%s ∩ %s = %s", this, value, result));
            return result;
        }
    }

    record MappingRange(Range range, Long destinationStart) implements Comparable<MappingRange> {
        static MappingRange fromString(String input) {
            String[] in = input.split(" ");
            return new MappingRange(new Range(Long.valueOf(in[1]), Long.valueOf(in[2])), Long.valueOf(in[0]));
        }

        boolean contains(Range value) {
            return range.contains(value);
        }

        public int compareTo(final MappingRange o) {
            return range.compareTo(o.range);
        }

        Range mapToDestination(Range value) {
            if (!range.fullyContains(value)) {
                throw new IllegalArgumentException("not in range");
            }
            return new Range(destinationStart + value.start - range.start, value.length);
        }

    }

}
