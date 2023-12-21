package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.Count;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.list.List;
import dev.notkili.aoc.shared.misc.tuple.Triple;
import dev.notkili.aoc.shared.parse.InputParser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicReference;

public class Day20 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day20::part1);
        TimeTracker.trackTime(Day20::part2);
    }

    public static void part1() {
        new InputParser(2023, 20).getInput().ifPresent(input -> {
            AtomicReference<Broadcast> broadcastRef = new AtomicReference<>();
            List<Module> modules = List.of();
            HashMap<String, java.util.List<String>> connections = new HashMap<>();
            HashMap<String, Module> moduleHashMap = new HashMap<>();

            input.splitAt("\n").forEach(line -> {
                var t = line.asTuple(" -> ");
                var id = t.getA();
                var mCon = t.getB().splitAt(", ").mapTo(StringInput::asString).asList();

                if (id.asString().equals("broadcaster")) {
                    var broadcast = new Broadcast(id.asString());
                    broadcastRef.set(broadcast);
                    modules.add(broadcast);
                    connections.put(id.asString(), mCon);
                    moduleHashMap.put(id.asString(), broadcast);
                } else {
                    var type = id.charAt(0);
                    id = id.substring(1);

                    if (type.asChar() == '%') {
                        var module = new FlipFlop(id.asString());
                        modules.add(module);
                        connections.put(id.asString(), mCon);
                        moduleHashMap.put(id.asString(), module);
                    } else if (type.asChar() == '&') {
                        var module = new Conjunction(id.asString());
                        modules.add(module);
                        connections.put(id.asString(), mCon);
                        moduleHashMap.put(id.asString(), module);
                    } else {
                        throw new RuntimeException("Invalid type");
                    }
                }
            });

            connections.forEach((moduleKey, conns) -> {
                var m = moduleHashMap.get(moduleKey);

                conns.forEach(con -> {
                    var c = moduleHashMap.get(con);

                    if (c == null) {
                        var module = new Test(con);
                        modules.add(module);
                        c = module;
                    }

                    if (c instanceof Conjunction) {
                        ((Conjunction) c).addInput(moduleKey);
                    }

                    m.connect(c);
                });
            });

            for (int i = 0; i < 1000; i++) {
                // Simulate push:
                var broadcast = broadcastRef.get();

                var queue = new LinkedList<>(broadcast.receive("button", Pulse.LOW).arrayList());

                while (!queue.isEmpty()) {
                    var next = queue.poll();

                    queue.addAll(next.getC().receive(next.getA(), next.getB()).arrayList());
                }
            }

            Count highCount = new Count(0);
            Count lowCount = new Count(0);

            modules.forEach(m -> {
                highCount.increment(m.highCount);
                lowCount.increment(m.lowCount);
            });

            new LongInput(highCount.getCount() * lowCount.getCount()).asSolution().print();
        });
    }

    public static void part2() {
        new InputParser(2023, 20).getInput().ifPresent(input -> {
            AtomicReference<Broadcast> broadcastRef = new AtomicReference<>();
            List<Module> modules = List.of();
            HashMap<String, java.util.List<String>> connections = new HashMap<>();
            HashMap<String, Module> moduleHashMap = new HashMap<>();

            input.splitAt("\n").forEach(line -> {
                var t = line.asTuple(" -> ");
                var id = t.getA();
                var mCon = t.getB().splitAt(", ").mapTo(StringInput::asString).asList();

                if (id.asString().equals("broadcaster")) {
                    var broadcast = new Broadcast(id.asString());
                    broadcastRef.set(broadcast);
                    modules.add(broadcast);
                    connections.put(id.asString(), mCon);
                    moduleHashMap.put(id.asString(), broadcast);
                } else {
                    var type = id.charAt(0);
                    id = id.substring(1);

                    if (type.asChar() == '%') {
                        var module = new FlipFlop(id.asString());
                        modules.add(module);
                        connections.put(id.asString(), mCon);
                        moduleHashMap.put(id.asString(), module);
                    } else if (type.asChar() == '&') {
                        var module = new Conjunction(id.asString());
                        modules.add(module);
                        connections.put(id.asString(), mCon);
                        moduleHashMap.put(id.asString(), module);
                    } else {
                        throw new RuntimeException("Invalid type");
                    }
                }
            });

            connections.forEach((moduleKey, conns) -> {
                var m = moduleHashMap.get(moduleKey);

                conns.forEach(con -> {
                    var c = moduleHashMap.get(con);

                    if (c == null) {
                        var module = new Test(con);
                        modules.add(module);
                        moduleHashMap.put(con, module);
                        c = module;
                    }

                    if (c instanceof Conjunction) {
                        ((Conjunction) c).addInput(moduleKey);
                    }

                    m.connect(c);
                });
            });

            var src = moduleHashMap.get("rx").parents.get(0).parents.map(Module::getId);
            var srcCycles = new HashMap<String, Long>();

            var cycleCount = 0L;

            while (srcCycles.size() != src.size()) {
                cycleCount++;
                var queue = new LinkedList<Triple<String, Pulse, Module>>();

                queue.add(new Triple<>("button", Pulse.LOW, broadcastRef.get()));

                while (!queue.isEmpty()) {
                    var next = queue.poll();

                    if (src.contains(next.getA())) {
                        if (next.getB() == Pulse.HIGH) {
                            srcCycles.put(next.getA(), Math.min(srcCycles.getOrDefault(next.getA(), Long.MAX_VALUE), cycleCount));
                        }
                    }

                    queue.addAll(next.getC().receive(next.getA(), next.getB()).arrayList());
                }
            }

            new LongInput(srcCycles.values().stream().reduce(1L, (a, b) -> a * b)).asSolution().print();
        });
    }

    public abstract static class Module {
        private final String id;

        protected final List<Module> connectedModules = List.of();

        protected final List<Module> parents = List.of();

        protected Count highCount = new Count(0);

        protected Count lowCount = new Count(0);

        public Module(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void connect(Module module) {
            connectedModules.add(module);
            module.parents.add(this);
        }

        public abstract List<Triple<String, Pulse, Module>> receive(String id, Pulse pulse);
    }

    public static class FlipFlop extends Module {
        private boolean on = false;

        public FlipFlop(String id) {
            super(id);
        }

        @Override
        public List<Triple<String, Pulse, Module>> receive(String id, Pulse pulse) {
            if (pulse == Pulse.HIGH) {
                highCount.increment();
            } else {
                lowCount.increment();
            }

            if (pulse == Pulse.LOW) {
                on = !on;

                if (on) {
                    return connectedModules.map(m -> new Triple<>(getId(), Pulse.HIGH, m));
                } else {
                    return connectedModules.map(m -> new Triple<>(getId(), Pulse.LOW, m));
                }
            }

            return List.of();
        }
    }

    public static class Conjunction extends Module {
        private final HashMap<String, Pulse> mostRecentPulses = new HashMap<>();

        public Conjunction(String id) {
            super(id);
        }

        public void addInput(String id) {
            mostRecentPulses.put(id, Pulse.LOW);
        }

        @Override
        public List<Triple<String, Pulse, Module>> receive(String id, Pulse pulse) {
            if (pulse == Pulse.HIGH) {
                highCount.increment();
            } else {
                lowCount.increment();
            }

            mostRecentPulses.put(id, pulse);

            if (mostRecentPulses.values().stream().filter(p -> p == Pulse.HIGH).count() == mostRecentPulses.size()) {
                return connectedModules.map(m -> new Triple<>(getId(), Pulse.LOW, m));
            } else {
                return connectedModules.map(m -> new Triple<>(getId(), Pulse.HIGH, m));
            }
        }
    }

    public static class Test extends Module {
        public Test(String id) {
            super(id);
        }

        @Override
        public List<Triple<String, Pulse, Module>> receive(String id, Pulse pulse) {
            if (pulse == Pulse.HIGH) {
                highCount.increment();
            } else {
                lowCount.increment();
            }

            return List.of();
        }
    }

    public static class Broadcast extends Module {
        public Broadcast(String id) {
            super(id);
        }

        @Override
        public List<Triple<String, Pulse, Module>> receive(String id, Pulse pulse) {
            if (pulse == Pulse.HIGH) {
                highCount.increment();
            } else {
                lowCount.increment();
            }

            return connectedModules.map(module -> new Triple<>(getId(), pulse, module));
        }
    }

    public enum Pulse {
        HIGH,
        LOW
    }
}
