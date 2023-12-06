package fr.fvieillard.adventofcode.year2022;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day07 extends Day2022 {

    /**
     * Variable to hold the whole hierarchy (root dir containing all subdirs recursively)
     */
    private Directory rootDir;
    /**
     * Variable to hold references to all directories (in a "flat" manner).
     */
    private List<Directory> dirs = new ArrayList<>();

    public Day07(InputStream input) {
        super(7, "No Space Left On Device", input);
        processInput();
    }

    public static void main(String... args) {
        new Day07(Day07.class.getResourceAsStream("day_07.txt")).printDay();
    }

    protected void processInput() {
        Scanner scanner = new Scanner(getInput());

        Directory currentDir = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("$ cd /")) { // Root dir (init)
                currentDir = new Directory("/");
                rootDir = currentDir;
            } else if (line.startsWith("$ cd ..")) {   // Going up
                currentDir = currentDir.parent;
            } else if (line.startsWith("$ cd ")) {  // Going down
                Directory newDir = new Directory(line.substring(5));
                currentDir.addSubDir(newDir);
                currentDir = newDir;
            } else if (line.matches("^\\d+.*")) {   // File size
                currentDir.addFile(Long.parseLong(line.split(" ")[0]));
            }
        }

//        System.out.printf("Parsed structure: %n%s%n", rootDir.structure());
//        System.out.printf("List of directories:%n");
//        dirs.forEach(System.out::println);
//        System.out.println();
    }

    @Override
    public Object getSolutionPart1() {
        return dirs.stream().mapToLong(Directory::totalSize).filter(s -> s <= 100000).sum();
    }

    @Override
    public Object getSolutionPart2() {
        long totalSpace = 70000000;
        long neededSpace = 30000000;
        long usedSpace = rootDir.totalSize();
        long needToClear = neededSpace - (totalSpace - usedSpace);
//        System.out.printf("Space used: %s / %s. I need to free at least %s%n", usedSpace, totalSpace, needToClear);
        return dirs.stream()
                .mapToLong(Directory::totalSize)
                .filter(s -> s >= needToClear)
                .min().getAsLong();
    }


    /**
     * Class to represent a Directory containing files with a total file size
     * and a list of subdirectories.
     */
    private class Directory {
        private String name;
        private Directory parent;
        private List<Directory> subdirs = new ArrayList<>();
        private long fileSize = 0;

        Directory(String name) {
            this.name = name;
            // On creation, we add to the big flat list of dirs
            dirs.add(this);
        }

        void addSubDir(Directory dir) {
            dir.parent = this;
            subdirs.add(dir);
        }
        void addFile(long size) {
            // We actually never need the details of the files, only the
            // cumulative size of all files in the folder
            fileSize += size;
        }
        long totalSize() {
            return subdirs.stream().mapToLong(Directory::totalSize).sum() + fileSize;
        }
        public String structure() {
            StringBuilder builder = new StringBuilder();
            builder.append(" - ").append(this).append("\n");
            builder.append("    file size: ").append(fileSize).append("\n");
            for (Directory dir: subdirs) {
                builder
                        .append("   ")
                        .append(dir.structure().replaceAll("\\n(.+)", "\n   $1"));
            }
            return builder.toString();
        }
        public String toString() {
            return String.format("%s (dir, size=%s)", name, totalSize());
        }
    }
}
