package fr.fvieillard.adventofcode.year2023;

import java.io.InputStream;
import java.lang.reflect.Method;

import fr.fvieillard.adventofcode.Day;

public abstract class Day2023 extends Day {

    public static void main(String... args) throws Exception {
        for (int i=1; i<=25; i++) {
            String className = String.format("fr.fvieillard.adventofcode.year2023.Day%02d", i);
            try {
                Method meths = Day2023.class.getClassLoader().loadClass(className).getMethod("main", String[].class);
                meths.invoke(null, (Object) new String[]{});
            } catch (ClassNotFoundException e) {}
        }
    }

    public Day2023(int day, String title, InputStream input) {
        super(2023, day, title, input);
    }
}
