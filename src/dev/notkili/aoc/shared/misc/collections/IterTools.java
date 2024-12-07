package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.collections.list.List;

import java.util.Collection;
import java.util.Iterator;

public class IterTools {
    public static Iterator<Integer> count(int start) {
        return count(start, null, 1);
    }

    public static Iterator<Integer> count(int start, Integer end) {
        return count(start, end, 1);
    }
    
    public static Iterator<Integer> count(int start, Integer end, int step) {
        return new Iterator<>() {
            private int s = start;
            private final Integer e = end;
            private final int st = step;

            @Override
            public boolean hasNext() {
                return e == null || s < e;
            }

            @Override
            public Integer next() {
                var tmp = s;
                s += st;
                return tmp;
            }
        };
    }
    
    public static <C> Iterator<C> cycle(Collection<C> c) {
        return cycle(new List<>(c));
    }
    
    public static <C> Iterator<C> cycle(List<C> l) {
        return new Iterator<C>() {
            private final List<C> list = l;
            private int at = 0;
            
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public C next() {
                var tmp = list.get(at);
                
                at = (at + 1) % list.size();
                
                return tmp;
            }
        };
    }
    
    public static <C> Iterator<C> repeat(C elem) {
        return repeat(elem, null);
    }

    public static <C> Iterator<C> repeat(C elem, Integer n) {
        return new Iterator<>() {
            private final C element = elem;
            private final Integer count = n;
            private int at = 0;
            
            @Override
            public boolean hasNext() {
                return count == null || at < count;
            }

            @Override
            public C next() {
                at++;
                return elem;
            }
        };
    }
    
    public static <C> List<List<C>> product(Collection<C> c) {
        return product(new List<>(c), 1);
    }

    public static <C> List<List<C>> product(Collection<C> c, int repeat) {
        return product(new List<>(c), repeat);
    }

    public static <C> List<List<C>> product(List<C> l) {
        return product(l, 1);
    }

    public static <C> List<List<C>> product(List<C> l, int repeat) {
        if (l.size() == 0 || repeat == 0) {
            return List.of();
        }

        List<List<C>> result = List.of();
        result.add(new List<C>());
        
        for (int i = 0; i < repeat; i++) {
            List<List<C>> newResult = List.of();
            
            for (List<C> combination : result) {
                for (C elem : l) {
                    newResult.add(combination.copy().add(elem));
                }
            }
            
            result = newResult;
        }
        
        return result;
    }
    
    public static <C> List<List<C>> permutations(Collection<C> c) {
        return permutations(new List<>(c), 1);
    }

    public static <C> List<List<C>> permutations(Collection<C> c, int len) {
        return permutations(new List<>(c), len);
    }

    public static <C> List<List<C>> permutations(List<C> c) {
        return permutations(c, 1);
    }

    public static <C> List<List<C>> permutations(List<C> c, int len) {
        List<List<C>> result = List.of();
        List<C> current = c.copy();
        permutationsHelper(result, List.of(), current, len);
        return result;
    }
    
    private static <C> void permutationsHelper(List<List<C>> result, List<C> temp, List<C> elems, int len) {
        if (temp.size() == len) {
            result.add(temp.copy());
            return;
        }
        
        for (int i = 0; i < elems.size(); i++) {
            var elem = elems.get(i);
            temp.add(elem);
            elems.rem(i);
            permutationsHelper(result, temp, elems, len);
            elems.add(i, elem);
            temp.rem(temp.size() - 1);
        }
    }

    public static <C> List<List<C>> combinations(Collection<C> c) {
        return combinations(new List<>(c), 1);
    }
    
    public static <C> List<List<C>> combinations(Collection<C> c, int len) {
        return combinations(new List<>(c), len);
    }

    public static <C> List<List<C>> combinations(List<C> c) {
        return combinations(c, 1);
    }

    public static <C> List<List<C>> combinations(List<C> c, int len) {
        List<List<C>> result = List.of();
        List<C> current = c.copy();
        combinationsHelper(result, List.of(), current, len, 0);
        return result;
    }
    
    private static <C> void combinationsHelper(List<List<C>> result, List<C> temp, List<C> elems, int len, int start) {
        if (temp.size() == len) {
            result.add(temp.copy());
            return;
        }

        for (int i = start; i < elems.size(); i++) {
            temp.add(elems.get(i));
            combinationsHelper(result, temp, elems, len, i + 1);
            temp.rem(temp.size() - 1);
        }
    }
}
