package dev.notkili.aoc.shared.misc;

public class TimeTracker {
    public static void trackTime(Runnable runnable) {
        long start = System.nanoTime();
        runnable.run();
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) / 1000000 + "ms");
    }
}
