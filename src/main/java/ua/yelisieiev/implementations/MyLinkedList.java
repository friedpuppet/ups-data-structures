package ua.yelisieiev.implementations;

import ua.yelisieiev.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

public class MyLinkedList<V> implements List<V> {
    private static class Node<E> {
        private E value;

        public Node(E value) {
            this.value = value;
        }

        private Node<E> next;
        private Node<E> prev;
    }

    private int size;
    private Node<V> first;
    private Node<V> last;

    @Override
    public Iterator<V> iterator() {
        return new MyIterator();
    }

    @Override
    public void add(V value) {
        Node<V> newNode = new Node<>(value);
        if (size == 0) {
            first = last = newNode;
        } else {
            last.next = newNode;
            newNode.prev = last;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(V value, int index) {
        checkIndexInBounds(index, size);
        if (size == index) {
            add(value);
        } else {
            Node<V> newNode = new Node<>(value);
            Node<V> nodeAtIndex = getNodeAtIndex(index);
            insertBeforeNode(newNode, nodeAtIndex);
        }
    }

    @Override
    public V remove(int index) {
        checkIndexInBounds(index);
        Node<V> removedNode = getNodeAtIndex(index);
        removeByNode(removedNode);
        return removedNode.value;
    }

    @Override
    public V get(int index) {
        checkIndexInBounds(index);
        return getNodeAtIndex(index).value;
    }

    @Override
    public V set(V value, int index) {
        checkIndexInBounds(index);
        Node<V> changedNode = getNodeAtIndex(index);
        V oldValue = changedNode.value;
        changedNode.value = value;
        return oldValue;
    }

    @Override
    public void clear() {
        first = last = null;
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
        Node<V> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (value == null && currentNode.value == null ||
                    currentNode.value != null && currentNode.value.equals(value)) {
                return i;
            }
            currentNode = currentNode.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(V value) {
        Node<V> currentNode = last;
        for (int i = size - 1; i >= 0; i--) {
            if (value == null && currentNode.value == null ||
                    currentNode.value != null && currentNode.value.equals(value)) {
                return i;
            }
            currentNode = currentNode.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (V v : this) {
            stringJoiner.add(String.valueOf(v));
        }
        return stringJoiner.toString();
    }

    private void insertBeforeNode(Node<V> newNode, Node<V> afterNode) {
        newNode.next = afterNode;
        if (afterNode == first) {
            first = newNode;
        } else {
            newNode.prev = afterNode.prev;
            newNode.prev.next = newNode;
        }
        afterNode.prev = newNode;
        size++;
    }

    private class MyIterator implements Iterator<V> {

        private Node<V> nextNode = first;
        private Node<V> currentNode;
        private boolean canBeRemoved;
//        private int currentIndex;

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public V next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            currentNode = nextNode;
            nextNode = nextNode.next;
//            currentIndex++;
            canBeRemoved = true;
            return currentNode.value;
        }

        @Override
        public void remove() {
            if (!canBeRemoved) {
                throw new IllegalStateException("No active entity for the iterator");
            }
            MyLinkedList.this.removeByNode(currentNode);
//            currentIndex--;
            canBeRemoved = false;
        }
    }

    private void removeByNode(Node<V> removedNode) {
        if (removedNode == first && removedNode == last) {
            first = last = null;
        } else if (removedNode == first) {
            first = first.next;
            first.prev = null;
        } else if (removedNode == last) {
            last = last.prev;
            last.next = null;
        } else {
            removedNode.next.prev = removedNode.prev;
            removedNode.prev.next = removedNode.next;
        }
        size--;
    }

    private Node<V> getNodeAtIndex(int index) {
        // TODO: always iterate from first to last - will optimize later
        Node<V> currentNode = first;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        throw new IllegalStateException("Internal size doesn't match actual list size");
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
}
