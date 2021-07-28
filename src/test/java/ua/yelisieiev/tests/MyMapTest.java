package ua.yelisieiev.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.yelisieiev.implementations.MyMap;
import ua.yelisieiev.interfaces.Map;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {
    private final Map<Integer, String> testMap = new MyMap<>(6, 0.5, 2);

    @BeforeEach
    void fillMapWith123() {
        testMap.put(1, "One");
        testMap.put(2, "Two");
        testMap.put(3, "Three");
    }

    // put
    @DisplayName("Put a new nonnull key with nonnull value and see that the size increases")
    @Test
    void test_put_NonNullKey_nonNullValue_sizeIncreased() {
        testMap.put(9, "Nine");
        assertEquals(4, testMap.size());
    }

    @DisplayName("Put a new nonnull key with nonnull value and see that old value is null")
    @Test
    void test_put_NonNullKey_nonNullValue_keyNotExist() {
        String oldValue = testMap.put(9, "Nine");
        assertNull(oldValue);
    }

    @DisplayName("Put an existing nonnull key with a nonnull value and see that the old value is returned")
    @Test
    void test_put_NonNullKey_nonNullValue_keyExists() {
        String oldValue = testMap.put(2, "TwoTwo");
        assertEquals("Two", oldValue);
    }

    @DisplayName("Put a new pair that the internal array grows and see that all the pairs are still accessible")
    @Test
    void test_GrowInternalArray_AndGetSameValues() {
        String oldValue = testMap.put(9, "Nine");
        assertEquals("One", testMap.get(1));
        assertEquals("Two", testMap.get(2));
        assertEquals("Three", testMap.get(3));
        assertEquals("Nine", testMap.get(9));
    }

    // get
    @DisplayName("Get an existing pair for nonnull key and see that the value is correct")
    @Test
    void test_getExistingNonNullKey_nonNullValue() {
        assertEquals("One", testMap.get(1));
    }

    @DisplayName("Get a pair with nonnull key and null value")
    @Test
    void test_getExistingNonNullKey_NullValue() {
        testMap.put(0, null);
        assertNull(testMap.get(0));
    }

    @DisplayName("Get a nonexistent pair for nonnull key and see that null is returned")
    @Test
    void test_getNonExistingNonNullKey_nullResult() {
        assertNull(testMap.get(9));
    }

    @DisplayName("Get by null key - throws NPE")
    @Test
    void testGetNullKey_NPE() {
        assertThrows(NullPointerException.class, () -> testMap.get(null));
    }

    // containsKey
    @DisplayName("Map contains known key")
    @Test
    void test_containsNonNullKey() {
        assertTrue(testMap.containsKey(1));
    }

    @DisplayName("Nonexistent key is not found")
    @Test
    void test_notContainsNonNullKey() {
        assertFalse(testMap.containsKey(9));
    }

    @DisplayName("Check for a null key - throws NPE")
    @Test
    void test_containsNullKey_NPE() {
        assertThrows(NullPointerException.class, () -> testMap.containsKey(null));
    }

    // remove
    @DisplayName("Remove existing pair - size decreased")
    @Test
    void test_removeExistingNonNullKey_decreaseSize() {
        testMap.remove(1);
        assertEquals(2, testMap.size());

    }

    @DisplayName("Remove existing pair with nonnull key, removed value returned")
    @Test
    void test_removeExistingNonNullKey_returnsNonNullOldValue() {
        testMap.put(9, "Nine");
        assertEquals("Nine", testMap.remove(9));
    }

    @DisplayName("Remove existing pair with nonnull key and null value, removed value returned")
    @Test
    void test_removeExistingNonNullKey_returnsNullOldValue() {
        testMap.put(0, null);
        assertNull(testMap.remove(0));
    }

    @DisplayName("Remove of nonexistent key returns null, size doesn't change")
    @Test
    void test_removeNonExistingNonNullKey_returnsNull() {
        assertNull(testMap.remove(0));
        assertEquals(3, testMap.size());
    }

    @DisplayName("Try to remove by null key - throws NPE")
    @Test
    void test_removeNullKey_NPE() {
        assertThrows(NullPointerException.class, () -> testMap.remove(null));
    }

    // size
    @DisplayName("The test map size should be 3")
    @Test
    void test_sizeIsThree() {
        assertEquals(3, testMap.size());
    }

    // iterator
    @DisplayName("Has next entity on nonempty map")
    @Test
    void test_iteratorHasNext() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        assertTrue(iterator.hasNext());
    }

    @DisplayName("Doesn't have next entity - at the end of the map")
    @Test
    void test_iteratorHasNoNext() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @DisplayName("Entities fetched by iterator are tha same that were put into the map")
    @Test
    void test_iteratorNextReturnsActualEntities() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        int[] expectedKeys = {1, 2, 3};
        int[] fetchedKeys = new int[3];
        fetchedKeys[0] = iterator.next().getKey();
        fetchedKeys[1] = iterator.next().getKey();
        fetchedKeys[2] = iterator.next().getKey();
        Arrays.sort(fetchedKeys);
        assertArrayEquals(expectedKeys, fetchedKeys);
    }

    @DisplayName("Doesn't have next entity - on empty map")
    @Test
    void test_iteratorHasNoNextOnEmptyMap() {
        Iterator<Map.Entity<Integer, String>> iterator = new MyMap<Integer, String>().iterator();
        assertFalse(iterator.hasNext());
    }

    @DisplayName("Next on a map that's not at it's end")
    @Test
    void test_iteratorNextOnMapNotAtTheEnd() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        assertNotNull(iterator.next());
    }

    @DisplayName("Throw NoSuchElement at the end of the map")
    @Test
    void test_iteratorNextAtTheEnd_NSE() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @DisplayName("Remove on existing element - size decreased")
    @Test
    void test_iteratorRemoveExistingElement_sizeDecreased() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        iterator.next();
        iterator.remove();
        assertEquals(2, testMap.size());
    }

    @DisplayName("Remove removed element - throws IllegalState")
    @Test
    void test_iteratorRemoveRemoved() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @DisplayName("Remove an element before any next() - throws IllegalState")
    @Test
    void test_iteratorRemoveElementBeforeAnyNext_IllegalStateException() {
        Iterator<Map.Entity<Integer, String>> iterator = testMap.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }
}