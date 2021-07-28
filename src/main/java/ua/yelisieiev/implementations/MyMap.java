package ua.yelisieiev.implementations;

import ua.yelisieiev.interfaces.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 10;
    public static final double DEFAULT_GROW_FACTOR = 0.75;
    public static final double DEFAULT_GROW_MULTIPLIER = 2;

    private MyArrayList<Entity<K, V>>[] buckets;
    private int size;
    private double growFactor;
    private double growMultiplier;

    public MyMap(int capacity, double growFactor, double growMultiplier) {
        this.growFactor = growFactor;
        this.growMultiplier = growMultiplier;
        buckets = new MyArrayList[capacity];
    }

    public MyMap(int capacity, double growFactor) {
        this(capacity, growFactor, DEFAULT_GROW_MULTIPLIER);
    }

    public MyMap(int capacity) {
        this(capacity, DEFAULT_GROW_FACTOR, DEFAULT_GROW_MULTIPLIER);
    }

    public MyMap() {
        this(DEFAULT_CAPACITY, DEFAULT_GROW_FACTOR, DEFAULT_GROW_MULTIPLIER);
    }

    @Override
    public V put(K key, V value) {
        Entity<K, V> oldEntity = findEntityByKey(key);
        if (oldEntity != null) {
            V oldValue = oldEntity.getValue();
            oldEntity.setValue(value);
            return oldValue;
        } else {
            if (size >= buckets.length * growFactor) {
                growBuckets();
            }
            addEntityToBuckets(new MyEntity(key, value));
            size++;
            return null;
        }
    }

    private void addEntityToBuckets(Entity<K, V> entity) {
        addEntityToBuckets(entity, buckets);
    }

    private void addEntityToBuckets(Entity<K, V> entity, MyArrayList<Entity<K, V>>[] modifiedBuckets) {
        int bucketIndex = Math.abs(entity.getKey().hashCode()) % modifiedBuckets.length;
        if (modifiedBuckets[bucketIndex] == null) {
            modifiedBuckets[bucketIndex] = new MyArrayList<>();
        }
        modifiedBuckets[bucketIndex].add(entity);
    }

    private void growBuckets() {
        int newCapacity = (int) (buckets.length * growMultiplier);
        MyArrayList<Entity<K, V>>[] newBuckets = new MyArrayList[newCapacity]; // TODO: clarify what's happenning here
        for (MyArrayList<Entity<K, V>> bucket : buckets) {
            if (bucket != null) {
                for (Entity<K, V> kvEntity : bucket) {
                    addEntityToBuckets(kvEntity, newBuckets);
                }
            }
        }
        buckets = newBuckets;
    }

    private Entity<K, V> findEntityByKey(K key) {
        return findOrRemoveEntityByKey(key, false);
    }

    private Entity<K, V> findOrRemoveEntityByKey(K key, boolean mustRemove) {
        int bucketIndex = Math.abs(key.hashCode()) % buckets.length;
        if (buckets[bucketIndex] == null) {
            return null;
        } else {
            Iterator<Entity<K, V>> iterator = buckets[bucketIndex].iterator();
            while (iterator.hasNext()) {
                Entity<K, V> kvEntity = iterator.next();
                if (kvEntity.getKey().equals(key)) {
                    if (mustRemove) {
                        iterator.remove();
                        size--;
                    }
                    return kvEntity;
                }
            }
        }
        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("Key can't be null");
        }
        Entity<K, V> entityByKey = findEntityByKey(key);
        if (entityByKey != null) {
            return entityByKey.getValue();
        } else {
            return null;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return findEntityByKey(key) != null;
    }

    @Override
    public V remove(K key) {
        Entity<K, V> removedEntity = findOrRemoveEntityByKey(key, true);
        if (removedEntity == null) {
            return null;
        } else {
            return removedEntity.getValue();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entity<K, V>> iterator() {
        return new MyMapIterator<>();
    }

    private class MyMapIterator<E> implements Iterator<Entity<K, V>> {
        private Entity<K, V> nextEntity;
        private Entity<K, V> currentEntity;
        private boolean canBeRemoved;
        private int nextBucketIndex;
        private int nextBucketListIndex = -1;

        public MyMapIterator() {
            nextEntity = findNextEntity();
        }

        @Override
        public boolean hasNext() {
            return nextEntity != null;
        }

        @Override
        public Entity<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more entities in the map");
            }
            currentEntity = nextEntity;
            nextEntity = findNextEntity();
            canBeRemoved = true;
            return currentEntity;
        }

        private Entity<K, V> findNextEntity() {
            nextBucketListIndex++;
            for (int i = nextBucketIndex; i < buckets.length; i++) {
                if (buckets[i] != null) {
                    for (int j = nextBucketListIndex; j < buckets[i].size(); j++) {
                        nextBucketIndex = i;
                        nextBucketListIndex = j;
                        return buckets[i].get(j);
                    }
                    nextBucketListIndex = 0;
                }
            }
            return null;
        }

        @Override
        public void remove() {
            if (!canBeRemoved) {
                throw new IllegalStateException("No active entity for the iterator");
            }
            findOrRemoveEntityByKey(currentEntity.getKey(), true);
            canBeRemoved = false;
        }
    }

    private class MyEntity implements Entity<K, V> {
        private final K key;
        private V value;

        public MyEntity(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "MyMapEntity{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
