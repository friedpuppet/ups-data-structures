package ua.yelisieiev.implementations;

import ua.yelisieiev.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class MyArrayList<V> implements List<V> {
    private static final int DEFAULT_CAPACITY = 10;
    private static final double DEFAULT_GROW_MULTIPLIER = 1.5;

    private Object[] container;
    private int capacity;
    private final double growMultiplier;
    private int size;

    public MyArrayList() {
        this(DEFAULT_CAPACITY, DEFAULT_GROW_MULTIPLIER);
    }

    public MyArrayList(int capacity, double growMultiplier) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity can't be negative");
        }
        if (growMultiplier <= 1) {
            throw new IllegalArgumentException("Grow multiplier must be bigger than 1");
        }
        this.capacity = capacity;
        this.growMultiplier = growMultiplier;
        container = new Object[capacity];
    }

    public MyArrayList(int capacity) {
        this(capacity, DEFAULT_GROW_MULTIPLIER);
    }

    @Override
    public void add(V value) {
        add(value, size);
    }

    @Override
    public void add(V value, int index) {
        checkIndexInBounds(index, size);

        if (size == capacity) {
            growContainer();
        }
        System.arraycopy(container, index, container, index + 1, size - index);
        container[index] = value;
        size++;
    }

    @Override
    public V remove(int index) {
        checkIndexInBounds(index);
        V removedValue = (V) container[index];
        System.arraycopy(container, index + 1, container, index, size - index - 1);
        container[size - 1] = null;
        size--;
        return removedValue;
    }

    @Override
    public V get(int index) {
        checkIndexInBounds(index);
        return (V) container[index];
    }

    @Override
    public V set(V value, int index) {
        checkIndexInBounds(index);
        V oldValue = (V) container[index];
        container[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        container = new Object[capacity];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(V value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(V value) {
        for (int i = 0; i < size; i++) {
            if (value != null) {
                if (value.equals(container[i])) {
                    return i;
                }
            } else if (container[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(V value) {
        for (int i = size - 1; i >= 0; i--) {
            if (value != null) {
                if (value.equals(container[i])) {
                    return i;
                }
            } else if (container[i] == null) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            stringJoiner.add(String.valueOf(container[i]));
        }
        return stringJoiner.toString();
    }

    private void growContainer() {
        // round up to ensure grow for at least 1
        int newCapacity = (int) Math.round(capacity * growMultiplier + 0.5);
        Object[] newContainer = new Object[newCapacity];
        System.arraycopy(container, 0, newContainer, 0, size);
        container = newContainer;
        capacity = newCapacity;
    }

    private void checkIndexInBounds(int index) {
        checkIndexInBounds(index, size - 1);
    }

    private void checkIndexInBounds(int index, int upperLimit) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index too small: " + index);
        }
        if (index > upperLimit) {
            throw new IndexOutOfBoundsException("Index too big: " + index);
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new MyIterator<V>();
    }

    private class MyIterator<E> implements Iterator<E> {
        private int currentIndex = -1;
        private boolean canBeRemoved = false;

        @Override
        public boolean hasNext() {
            return currentIndex < size - 1;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            canBeRemoved = true;
            currentIndex++;
            return (E) container[currentIndex];
        }

        @Override
        public void remove() {
            if (!canBeRemoved) {
                throw new IllegalStateException("No active entity for the iterator");
            }
            MyArrayList.this.remove(currentIndex);
            currentIndex--;
            canBeRemoved = false;
        }
    }

}
