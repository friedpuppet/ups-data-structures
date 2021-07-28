package ua.yelisieiev.interfaces;

public interface Map<K, V> extends Iterable<Map.Entity<K, V>> {
    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    int size();

    interface Entity<K, V> {
        K getKey();

        V getValue();

        void setValue(V value);
    }
}