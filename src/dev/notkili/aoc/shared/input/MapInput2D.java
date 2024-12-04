package dev.notkili.aoc.shared.input;

import dev.notkili.aoc.shared.misc.collections.set.Set;
import dev.notkili.aoc.shared.misc.functional.Function;
import dev.notkili.aoc.shared.misc.position.Int2DPos;
import dev.notkili.aoc.shared.misc.tuple.Tuple;

import java.util.HashMap;
import java.util.Map;

public class MapInput2D<V> {
    public static MapInput2D<CharInput> parse(StringInput input) {
        return new MapInput2D<>(ArrayInput2D.parse(input).getBackingArray());
    }
    
    public static <V> MapInput2D<V> parse(StringInput input, Function<CharInput, V> mapper) {
        var backingArray = ArrayInput2D.parse(input).getBackingArray();
        var map = new HashMap<Int2DPos, V>();
        
        for (int y = 0; y < backingArray.length; y++) {
            for (int x = 0; x < backingArray[y].length; x++) {
                map.put(new Int2DPos(x, y), mapper.apply(backingArray[y][x]));
            }
        }
        
        return new MapInput2D<>(map);
    }
    
    private final int xMax;
    private final int yMax;
    private final Map<Int2DPos, V> backingMap;
    
    public MapInput2D(V[][] backingArray) {
        this.backingMap = new HashMap<>();
        
        this.xMax = backingArray[0].length;
        this.yMax = backingArray.length;
        
        for (int y = 0; y < backingArray.length; y++) {
            for (int x = 0; x < backingArray[y].length; x++) {
                backingMap.put(new Int2DPos(x, y), backingArray[y][x]);
            }
        }
    }
    
    public MapInput2D(Map<Int2DPos, V> backingMap) {
        this.xMax = backingMap.keySet().stream().mapToInt(Int2DPos::getX).max().orElse(0);
        this.yMax = backingMap.keySet().stream().mapToInt(Int2DPos::getY).max().orElse(0);
        this.backingMap = backingMap;
    }
    
    public int width() {
        return xMax;
    }
    
    public int height() {
        return yMax;
    }
    
    public boolean contains(int x, int y) {
        return backingMap.containsKey(new Int2DPos(x, y));
    }
    
    public boolean contains(IntInput x, IntInput y) {
        return backingMap.containsKey(new Int2DPos(x, y));
    }
    
    public boolean contains(Tuple<Integer, Integer> pos) {
        return backingMap.containsKey(new Int2DPos(pos.getA(), pos.getB()));
    }
    
    public boolean contains(Int2DPos pos) {
        return backingMap.containsKey(pos);
    }
    
    public V get(int x, int y) {
        return backingMap.get(new Int2DPos(x, y));
    }
    
    public V get(IntInput x, IntInput y) {
        return backingMap.get(new Int2DPos(x, y));
    }
    
    public V get(Tuple<Integer, Integer> pos) {
        return backingMap.get(new Int2DPos(pos.getA(), pos.getB()));
    }
    
    public V get(Int2DPos pos) {
        return backingMap.get(pos);
    }
    
    public void put(int x, int y, V value) {
        backingMap.put(new Int2DPos(x, y), value);
    }
    
    public void put(IntInput x, IntInput y, V value) {
        backingMap.put(new Int2DPos(x, y), value);
    }
    
    public void put(Tuple<Integer, Integer> pos, V value) {
        backingMap.put(new Int2DPos(pos.getA(), pos.getB()), value);
    }
    
    public void put(Int2DPos pos, V value) {
        backingMap.put(pos, value);
    }
    
    public Set<Int2DPos> positions() {
        return new Set<>(backingMap.keySet());
    }
    
    public Map<Int2DPos, V> map() {
        return backingMap;
    }
    
    public V[][] array() {
        var array = (V[][]) new Object[yMax][xMax];
        
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                array[y][x] = get(x, y);
            }
        }
        
        return array;
    }
    
    public MapInput2D<V> copy() {
        return new MapInput2D<>(new HashMap<>(backingMap));
    }
}
