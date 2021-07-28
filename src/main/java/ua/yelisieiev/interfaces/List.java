package ua.yelisieiev.interfaces;

public interface List <V> extends Iterable<V> {
    // add value to the end of the list
    void add(V value);

    // [A, B, C, null, null ] size = 3
    // add (D, [0,1,2,3])
    // we can add value by index between [0, size]
    // otherwise throw new IndexOutOfBoundsException
    void add(V value, int index) throws IndexOutOfBoundsException;

    // we can remove value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException

    // [A, B, C] remove = 0
    // [B (index = 0) , C (index = 1)]
    V remove(int index);

    // [A, B, C] size = 3
    // we can get value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    V get(int index);

    // we can set value by index between [0, size - 1]
    // otherwise throw new IndexOutOfBoundsException
    V set(V value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(V value);

    // [A, B, A, C] indexOf(A) -> 0
    // -1 if not exist
    int indexOf(V value);

    // [A, B, A, C] lastIndexOf(A) -> 2
    int lastIndexOf(V value);

    // [A, B, C]
    String toString();
}