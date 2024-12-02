package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.ArrayInput2D;
import dev.notkili.aoc.shared.input.CharInput;
import dev.notkili.aoc.shared.input.IntInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.Arrays;
import java.util.HashMap;

public class Day14 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day14::part1);
        TimeTracker.trackTime(Day14::part2);
    }

    private static void part1() {
        new InputParser(2023, 14).getInput().ifPresent(input -> {
            var platform = new Platform(ArrayInput2D.parse(input).getBackingArray());
            platform.tiltNorth();
            platform.calcLoad().solution().print(); //.submit(2023, 14, 1);

        });
    }

    private static void part2() {
        new InputParser(2023, 14).getInput().ifPresent(input -> {
            var platform = new Platform(ArrayInput2D.parse(input).getBackingArray());

            var set = new Set<Platform>();
            var platformToCycle = new HashMap<Platform, Integer>();
            var cycleToPlatform = new HashMap<Integer, Platform>();

            int cycle = 0;

            while (true) {
                cycle++;

                platform.cycle();

                if (set.contains(platform)) {
                    break;
                }

                var cyp = platform.copy();
                platformToCycle.put(cyp, cycle);
                cycleToPlatform.put(cycle, cyp);
                set.add(cyp);
            }

            var loopStart = platformToCycle.get(platform);
            var mod = (int) ((1000000000L - loopStart) % (cycle - loopStart)) + loopStart;

            cycleToPlatform.get(mod).calcLoad().solution().print(); //.submit(2023, 14, 2);
        });
    }

    private static class Platform {
        private CharInput[][] map;

        public Platform(CharInput[][] map) {
            this.map = map;
        }

        public void cycle() {
            tiltNorth();
            tiltWest();
            tiltSouth();
            tiltEast();
        }

        public void tiltNorth() {
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (map[y][x].equals(new CharInput('O'))) {
                        int delta = 0;

                        while (y - (delta + 1) >= 0 && map[y - (delta + 1)][x].equals(new CharInput('.'))) {
                            delta++;
                        }

                        map[y][x] = new CharInput('.');
                        map[y - delta][x] = new CharInput('O');
                    }
                }
            }
        }

        public void tiltSouth() {
            for (int y = map.length - 1; y >= 0; y--) {
                for (int x = 0; x < map[y].length; x++) {
                    if (map[y][x].equals(new CharInput('O'))) {
                        int delta = 0;

                        while (y + (delta + 1) < map.length && map[y + (delta + 1)][x].equals(new CharInput('.'))) {
                            delta++;
                        }

                        map[y][x] = new CharInput('.');
                        map[y + delta][x] = new CharInput('O');
                    }
                }
            }
        }

        public void tiltEast() {
            for (int x = map[0].length - 1; x >= 0; x--) {
                for (int y = 0; y < map.length; y++) {
                    if (map[y][x].equals(new CharInput('O'))) {
                        int delta = 0;

                        while (x + (delta + 1) < map[y].length && map[y][x + (delta + 1)].equals(new CharInput('.'))) {
                            delta++;
                        }

                        map[y][x] = new CharInput('.');
                        map[y][x + delta] = new CharInput('O');
                    }
                }
            }
        }

        public void tiltWest() {
            for (int x = 0; x < map[0].length; x++) {
                for (int y = 0; y < map.length; y++) {
                    if (map[y][x].equals(new CharInput('O'))) {
                        int delta = 0;

                        while (x - (delta + 1) >= 0 && map[y][x - (delta + 1)].equals(new CharInput('.'))) {
                            delta++;
                        }

                        map[y][x] = new CharInput('.');
                        map[y][x - delta] = new CharInput('O');
                    }
                }
            }
        }

        public IntInput calcLoad() {
            IntInput load = new IntInput(0);

            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    if (map[y][x].equals(new CharInput('O'))) {
                        load = load.add(map.length - y);
                    }
                }
            }

            return load;
        }

        public Platform copy() {
            var newMap = new CharInput[map.length][map[0].length];

            for (int y = 0; y < map.length; y++) {
                newMap[y] = Arrays.copyOf(map[y], map[y].length);
            }

            return new Platform(newMap);
        }

        public void print() {
            new ArrayInput2D<>(map).print();
        }

        @Override
        public int hashCode() {
            int code = 0;

            for (int y = 0; y < map.length; y++) {
                code += Arrays.hashCode(map[y]);
            }

            return code;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Platform other)) {
                return false;
            }

            for (int y = 0; y < map.length; y++) {
                if (!Arrays.equals(map[y], other.map[y])) {
                    return false;
                }
            }

            return true;
        }
    }
}
