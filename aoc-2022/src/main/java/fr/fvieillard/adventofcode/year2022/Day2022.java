package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.lang.reflect.Method;

import fr.fvieillard.adventofcode.Day;

public abstract class Day2022 extends Day {

    public static void main(String... args) throws Exception {
        for (int i=1; i<=25; i++) {
            String className = String.format("fr.fvieillard.adventofcode.year2022.Day%02d", i);
            try {
                Method meths = Day2022.class.getClassLoader().loadClass(className).getMethod("main", String[].class);
                meths.invoke(null, (Object) new String[]{});
            } catch (Exception e) {
                System.err.println("Exception");
            }
        }
    }

    public Day2022(int day, String title, InputStream input) {
        super(2022, day, title, input);
    }
}
