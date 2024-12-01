package dev.notkili.aoc.shared.misc.collections;

import dev.notkili.aoc.shared.misc.functional.*;

import java.util.HashMap;

public abstract class Cache {
    public static <K1, V> Cache.Un<K1, V> of(Function<K1, V> creator) {
        return new Un<>(creator);
    }

    public static <K1, K2, V> Cache.Bi<K1, K2, V> of(BiFunction<K1, K2, V> creator, Integer... keyArgs) {
        return new Bi<>(creator, keyArgs);
    }

    public static <K1, K2, K3, V> Cache.Tri<K1, K2, K3, V> of(TriFunction<K1, K2, K3, V> creator, Integer... keyArgs) {
        return new Tri<>(creator, keyArgs);
    }

    public static <K1, K2, K3, K4, V> Cache.Quad<K1, K2, K3, K4, V> of(QuadFunction<K1, K2, K3, K4, V> creator, Integer... keyArgs) {
        return new Quad<>(creator, keyArgs);
    }
    
    public static <K1, K2, K3, K4, K5, V> Cache.Quint<K1, K2, K3, K4, K5, V> of(QuintFunction<K1, K2, K3, K4, K5, V> creator, Integer... keyArgs) {
        return new Quint<>(creator, keyArgs);
    }
    
    public static <K1, K2, K3, K4, K5, K6, V> Cache.Sext<K1, K2, K3, K4, K5, K6, V> of(SextFunction<K1, K2, K3, K4, K5, K6, V> creator, Integer... keyArgs) {
        return new Sext<>(creator, keyArgs);
    }
    
    public static class Un<K, V> {
        private final HashMap<K, V> cache = new HashMap<>();
        private final Function<K, V> creator;
        
        public Un(Function<K, V> creator) {
            this.creator = creator;
        }
        
        public V get(K key) {
            return cache.computeIfAbsent(key, creator::apply);
        }
        
        public Un<K, V> cpy() {
            var tmp = new Un<>(creator);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
    
    public static class Bi<K1, K2, V> {
        private final HashMap<CombinedKey, V> cache = new HashMap<>();
        private final BiFunction<K1, K2, V> creator;
        private final Integer[] keyArgs;
        
        public Bi(BiFunction<K1, K2, V> creator, Integer... keyArgs) {
            this.creator = creator;
            this.keyArgs = keyArgs;
        }
        
        public V get(K1 key1, K2 key2) {
            var key = CombinedKey.of();
            
            for (var i : keyArgs) {
                switch (i) {
                    case 0 -> key.add(key1);
                    case 1 -> key.add(key2);
                }
            }
            
            return cache.computeIfAbsent(key, k -> creator.apply(key1, key2));
        }
        
        public Bi<K1, K2, V> cpy() {
            var tmp = new Bi<>(creator, keyArgs);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
    
    public static class Tri<K1, K2, K3, V> {
        private final HashMap<CombinedKey, V> cache = new HashMap<>();
        private final TriFunction<K1, K2, K3, V> creator;
        private final Integer[] keyArgs;
        
        public Tri(TriFunction<K1, K2, K3, V> creator, Integer... keyArgs) {
            this.creator = creator;
            this.keyArgs = keyArgs;
        }
        
        public V get(K1 key1, K2 key2, K3 key3) {
            var key = CombinedKey.of();
            
            for (var i : keyArgs) {
                switch (i) {
                    case 0 -> key.add(key1);
                    case 1 -> key.add(key2);
                    case 2 -> key.add(key3);
                }
            }
            
            return cache.computeIfAbsent(key, k -> creator.apply(key1, key2, key3));
        }
        
        public Tri<K1, K2, K3, V> cpy() {
            var tmp = new Tri<>(creator, keyArgs);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
    
    public static class Quad<K1, K2, K3, K4, V> {
        private final HashMap<CombinedKey, V> cache = new HashMap<>();
        private final QuadFunction<K1, K2, K3, K4, V> creator;
        private final Integer[] keyArgs;
        
        public Quad(QuadFunction<K1, K2, K3, K4, V> creator, Integer... keyArgs) {
            this.creator = creator;
            this.keyArgs = keyArgs;
        }
        
        public V get(K1 key1, K2 key2, K3 key3, K4 key4) {
            var key = CombinedKey.of();
            
            for (var i : keyArgs) {
                switch (i) {
                    case 0 -> key.add(key1);
                    case 1 -> key.add(key2);
                    case 2 -> key.add(key3);
                    case 3 -> key.add(key4);
                }
            }
            
            return cache.computeIfAbsent(key, k -> creator.apply(key1, key2, key3, key4));
        }
        
        public Quad<K1, K2, K3, K4, V> cpy() {
            var tmp = new Quad<>(creator, keyArgs);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
    
    public static class Quint<K1, K2, K3, K4, K5, V> {
        private final HashMap<CombinedKey, V> cache = new HashMap<>();
        private final QuintFunction<K1, K2, K3, K4, K5, V> creator;
        private final Integer[] keyArgs;
        
        public Quint(QuintFunction<K1, K2, K3, K4, K5, V> creator, Integer... keyArgs) {
            this.creator = creator;
            this.keyArgs = keyArgs;
        }
        
        public V get(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5) {
            var key = CombinedKey.of();
            
            for (var i : keyArgs) {
                switch (i) {
                    case 0 -> key.add(key1);
                    case 1 -> key.add(key2);
                    case 2 -> key.add(key3);
                    case 3 -> key.add(key4);
                    case 4 -> key.add(key5);
                }
            }
            
            return cache.computeIfAbsent(key, k -> creator.apply(key1, key2, key3, key4, key5));
        }
        
        public Quint<K1, K2, K3, K4, K5, V> cpy() {
            var tmp = new Quint<>(creator, keyArgs);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
    
    public static class Sext<K1, K2, K3, K4, K5, K6, V> {
        private final HashMap<CombinedKey, V> cache = new HashMap<>();
        private final SextFunction<K1, K2, K3, K4, K5, K6, V> creator;
        private final Integer[] keyArgs;
        
        public Sext(SextFunction<K1, K2, K3, K4, K5, K6, V> creator, Integer... keyArgs) {
            this.creator = creator;
            this.keyArgs = keyArgs;
        }
        
        public V get(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5, K6 key6) {
            var key = CombinedKey.of();
            
            for (var i : keyArgs) {
                switch (i) {
                    case 0 -> key.add(key1);
                    case 1 -> key.add(key2);
                    case 2 -> key.add(key3);
                    case 3 -> key.add(key4);
                    case 4 -> key.add(key5);
                    case 5 -> key.add(key6);
                }
            }
            
            return cache.computeIfAbsent(key, k -> creator.apply(key1, key2, key3, key4, key5, key6));
        }
        
        public Sext<K1, K2, K3, K4, K5, K6, V> cpy() {
            var tmp = new Sext<>(creator, keyArgs);
            tmp.cache.putAll(cache);
            return tmp;
        }
    }
}
