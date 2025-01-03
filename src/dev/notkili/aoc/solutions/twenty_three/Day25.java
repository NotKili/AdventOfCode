package dev.notkili.aoc.solutions.twenty_three;

import dev.notkili.aoc.shared.input.LongInput;
import dev.notkili.aoc.shared.input.StringInput;
import dev.notkili.aoc.shared.misc.TimeTracker;
import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.parse.InputParser;
import org.jgrapht.Graph;
import org.jgrapht.alg.StoerWagnerMinimumCut;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;

public class Day25 {
    public static void main(String[] args) {
        TimeTracker.trackTime(Day25::part1);
        TimeTracker.trackTime(Day25::part2);
    }

    public static void part1() {
        new InputParser(2023, 25).getInput().ifPresent(input -> {
            var nodes = new HashMap<String, Node>();

            input.split("\n").mapTo(s ->  s.tuple(": ")).forEach(t -> {
                var node = nodes.computeIfAbsent(t.getA().str(), Node::new);
                var connections = t.getB().split(" ").mapTo(StringInput::str).asList();

                for (var connection: connections) {
                    var other = nodes.computeIfAbsent(connection, Node::new);
                    node.connections.add(other);
                    other.connections.add(node);
                }
            });

            Graph<Node, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
            for (var node : nodes.values()) {
                graph.addVertex(node);
            }
            for (var node : nodes.values()) {
                for (var connection : node.connections) {
                    graph.addEdge(node, connection);
                }
            }

            var minCut = new StoerWagnerMinimumCut<>(graph).minCut();

            new LongInput(minCut.size() * (nodes.size() - minCut.size())).solution().print();
        });
    }

    public static void part2() {
        new InputParser(2023, 25).getInput().ifPresent(input -> {
            new StringInput("Just press the button").solution().print();
        });
    }

    public static class Node {
        private final String id;

        private final Set<Node> connections = new Set<>();

        public Node(String id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return id.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Node other && other.id.equals(id);
        }
    }
}
