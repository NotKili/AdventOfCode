package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.collections.list.List;

import java.util.Objects;

public class CombinedKey {
    public static CombinedKey of(Object... keys) {
        return new CombinedKey(keys);
    }

    private final List<Object> keys;

    public CombinedKey(List<Object> keys) {
        this.keys = keys;
    }

    public CombinedKey(Object... keys) {
        this.keys = List.of(keys);
    }
    
    public CombinedKey add(Object key) {
        keys.add(key);
        return this;
    }
    
    public CombinedKey add(int index, Object key) {
        keys.add(index, key);
        return this;
    }
    
    public CombinedKey remove(int index) {
        keys.rem(index);
        return this;
    }
    
    public CombinedKey remove(Object key) {
        keys.rem(key);
        return this;
    }
    
    public Object get(int index) {
        return keys.get(index);
    }
    
    public <T> T get(int index, Class<T> clazz) {
        return clazz.cast(get(index));
    }
    
    public <T> T as(int index) {
        return (T) get(index);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinedKey that = (CombinedKey) o;
        return Objects.equals(keys, that.keys);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(keys);
    }
}
