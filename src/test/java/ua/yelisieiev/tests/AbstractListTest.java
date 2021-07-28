package ua.yelisieiev.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.yelisieiev.implementations.MyArrayList;
import ua.yelisieiev.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractListTest {
    protected List<String> listOfThree = createList();

    protected abstract List<String> createList();

    @BeforeEach
    private void fillListOfThree() {
        listOfThree.add("A");
        listOfThree.add("B");
        listOfThree.add("C");
    }

    // add
    @DisplayName("Add an element to the end of the list")
    @Test
    public void testAdd() {
        listOfThree.add("T");
        assertEquals("T", listOfThree.get(listOfThree.size() - 1));
    }

    // addByIndex
    @DisplayName("Add an element at the start of the list")
    @Test
    public void testAddByIndex_AtBegin() {
        int targetIndex = 0;
        listOfThree.add("T", targetIndex);
        assertEquals("T", listOfThree.get(targetIndex));
    }

    @DisplayName("Add an element at negative index - throws IndexOutOfBoundsException")
    @Test
    public void testAddByIndex_BeforeZero_OutOfBounds() {
        int targetIndex = -1;
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.add("T", targetIndex));
    }

    @DisplayName("Add an element by index at the end of the list")
    @Test
    public void testAddByIndex_AtEnd() {
        int targetIndex = listOfThree.size();
        listOfThree.add("T", targetIndex);
        assertEquals("T", listOfThree.get(targetIndex));
    }

    @DisplayName("Add an element at index bigger than size - throws IndexOutOfBoundsException")
    @Test
    public void testAddByIndex_AboveEnd_OutOfBounds() {
        int targetIndex = listOfThree.size() + 1;
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.add("T", targetIndex));
    }

    @DisplayName("Add an element somewhere in the middle of the list")
    @Test
    public void testAddByIndex_InBetween() {
        int targetIndex = 1;
        listOfThree.add("T", targetIndex);
        assertEquals("T", listOfThree.get(targetIndex));
    }

    @DisplayName("Add a NULL somewhere in the middle of the list")
    @Test
    public void testAddNullByIndex_InBetween() {
        int targetIndex = 1;
        listOfThree.add(null, targetIndex);
        assertNull(listOfThree.get(targetIndex));
    }

    @DisplayName("Remove an element somewhere from the middle of the list")
    @Test
    public void testRemove_InBetween() {
        int targetIndex = 1;
        listOfThree.remove(targetIndex);
        assertEquals(2, listOfThree.size());
    }

    // remove
    @DisplayName("Remove first element of the list")
    @Test
    public void testRemove_AtZero() {
        int targetIndex = 0;
        listOfThree.remove(targetIndex);
        assertEquals(2, listOfThree.size());
    }

    @DisplayName("Remove element with negative index - throws IndexOutOfBoundsException")
    @Test
    public void testRemove_BeforeZero_OutOfBounds() {
        int targetIndex = -1;
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.remove(targetIndex));
    }

    @DisplayName("Remove element from the end of the list")
    @Test
    public void testRemove_AtEnd() {
        int targetIndex = listOfThree.size() - 1;
        listOfThree.remove(targetIndex);
        assertEquals(2, listOfThree.size());
    }

    @DisplayName("Remove element with index bigger than size-1 - throws IndexOutOfBoundsException")
    @Test
    public void testRemove_AboveEnd_OutOfBounds() {
        int targetIndex = listOfThree.size();
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.remove(targetIndex));
    }

    // get
    @DisplayName("Get an element somewhere from between the list")
    @Test
    public void testGet_InBetween() {
        int targetIndex = 1;
        String searchedString = listOfThree.get(targetIndex);
        assertNotNull(searchedString);
    }

    @DisplayName("Get first element of the list")
    @Test
    public void testGet_AtZero() {
        int targetIndex = 0;
        String searchedString = listOfThree.get(targetIndex);
        assertNotNull(searchedString);
    }

    @DisplayName("Get an element with negative index - throws IndexOutOfBoundsException")
    @Test
    public void testGet_BeforeZero_OutOfBounds() {
        int targetIndex = -1;
        assertThrows(IndexOutOfBoundsException.class, () -> listOfThree.get(targetIndex));
    }

    @DisplayName("Get an element from the end of the list")
    @Test
    public void testGet_AtEnd() {
        int targetIndex = listOfThree.size() - 1;
        String searchedString = listOfThree.get(targetIndex);
        assertNotNull(searchedString);
    }

    @DisplayName("Get an element with index bigger than size-1 - throws IndexOutOfBoundsException")
    @Test
    public void testGet_AboveEnd_OutOfBounds() {
        int targetIndex = listOfThree.size();
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.get(targetIndex));
    }

    // set
    @DisplayName("Set value to an element somewhere in the middle of the list and get old value")
    @Test
    public void testSet_InBetween_ReturnsCorrect() {
        int targetIndex = 1;
        String oldValue = listOfThree.get(targetIndex);
        // no spec, so i'm assuming the method returns old value
        String beforeSetValue = listOfThree.set("V", targetIndex);
        assertEquals(oldValue, beforeSetValue);
    }

    @DisplayName("Set value to an element somewhere in the middle of the list and check new value")
    @Test
    public void testSet_InBetween_SetsCorrect() {
        int targetIndex = 1;
        listOfThree.set("V", targetIndex);
        assertEquals("V", listOfThree.get(targetIndex));
    }

    @DisplayName("Set null value to an element somewhere in the middle of the list and check new value")
    @Test
    public void testSetNull_InBetween_SetsCorrect() {
        int targetIndex = 1;
        listOfThree.set(null, targetIndex);
        assertNull(listOfThree.get(targetIndex));
    }

    @DisplayName("Set value to the first element of the list and check new value")
    @Test
    public void testSet_AtZero() {
        int targetIndex = 0;
        listOfThree.set("V", targetIndex);
        assertEquals("V", listOfThree.get(targetIndex));
    }

    @DisplayName("Set value to an element with negative index - throws IndexOutOfBoundsException")
    @Test
    public void testSet_BeforeZero_OutOfBounds() {
        int targetIndex = -1;
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.set("V", targetIndex));
    }

    @DisplayName("Set value to the last element of the list")
    @Test
    public void testSet_AtEnd() {
        int targetIndex = listOfThree.size() - 1;
        listOfThree.set("V", targetIndex);
        assertEquals("V", listOfThree.get(targetIndex));
    }

    @DisplayName("Set value to an element with index bigger than size-1 - throws IndexOutOfBoundsException")
    @Test
    public void testSet_AboveEnd_OutOfBounds() {
        int targetIndex = listOfThree.size();
        assertThrows(IndexOutOfBoundsException.class,
                () -> listOfThree.set("V", targetIndex));
    }

    //clear
    @DisplayName("Clear the list and check size")
    @Test
    public void testClear() {
        listOfThree.clear();
        assertEquals(0, listOfThree.size());
    }

    // size
    @DisplayName("Simple size test")
    @Test
    public void testSize() {
        assertEquals(3, listOfThree.size());
    }

    // isEmpty
    @DisplayName("Check empty on newly created list")
    @Test
    public void testIsEmpty_Empty() {
        listOfThree = new MyArrayList<>();
        assertTrue(listOfThree.isEmpty());
    }

    @DisplayName("Check empty on nonempty list")
    @Test
    public void testIsEmpty_NonEmpty() {
        assertFalse(listOfThree.isEmpty());
    }

    // contains
    @DisplayName("Check contains on existing element that is not null")
    @Test
    public void testContains_ElemNotNullExists() {
        listOfThree.add("V");
        assertTrue(listOfThree.contains("V"));
    }

    @DisplayName("Check contains on existing null element")
    @Test
    public void testContains_ElemNullExists() {
        listOfThree.add(null);
        assertTrue(listOfThree.contains(null));
    }

    @DisplayName("Check contains on nonexistent element that is not null")
    @Test
    public void testContains_ElemNotNullNotExists() {
        assertFalse(listOfThree.contains("V"));
    }

    @DisplayName("Check contains on nonexistent null element")
    @Test
    public void testContains_ElemNullNotExists() {
        assertFalse(listOfThree.contains(null));
    }

    // indexOf
    @DisplayName("Get first index of an existing nonnull element")
    @Test
    public void testIndexOf_NonNullElemExists() {
        assertEquals(1, listOfThree.indexOf("B"));
    }

    @DisplayName("Get first index of an existing null element")
    @Test
    public void testIndexOf_NullElemExists() {
        listOfThree.add(null);
        assertEquals(3, listOfThree.indexOf(null));
    }

    @DisplayName("Get first index of a nonexistent nonnull element - returns -1")
    @Test
    public void testIndexOf_NonNullElemNotExists() {
        assertEquals(-1, listOfThree.indexOf("V"));
    }

    @DisplayName("Get first index of a nonexistent null element - returns -1")
    @Test
    public void testIndexOf_NullElemNotExists() {
        assertEquals(-1, listOfThree.indexOf(null));
    }

    @DisplayName("Get last index of an existing element")
    @Test
    public void testLastIndexOf_ElemExists() {
        listOfThree.add("B");
        listOfThree.add("N");
        listOfThree.add("M");
        assertEquals(3, listOfThree.lastIndexOf("B"));
    }

    @DisplayName("Get last index of a nonexistent element - returns -1")
    @Test
    public void testLastIndexOf_ElemNotExists() {
        assertEquals(-1, listOfThree.indexOf("V"));
    }

    // toString
    @DisplayName("toString returns as documented")
    @Test
    public void testToString() {
        listOfThree.add(null);
        assertEquals("[A, B, C, null]", listOfThree.toString());
    }

    // iterator
    @DisplayName("Iterator has next element")
    @Test
    public void testIteratorHasNext() {
        Iterator<String> iterator = listOfThree.iterator();
        assertTrue(iterator.hasNext());
    }

    @DisplayName("Iterator has no next element")
    @Test
    public void testIteratorHasNext_NoNext() {
        Iterator<String> iterator = listOfThree.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertFalse(iterator.hasNext());
    }

    @DisplayName("Next returns correct value")
    @Test
    public void testIteratorNext() {
        Iterator<String> iterator = listOfThree.iterator();
        assertEquals("A", iterator.next());
    }

    @DisplayName("Next at the end of the list - throws NoSuchElementException")
    @Test
    public void testIteratorNext_NoSuchElement() {
        Iterator<String> iterator = listOfThree.iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @DisplayName("Remove decreases list size")
    @Test
    public void testIteratorRemove_DecreaseSize() {
        Iterator<String> iterator = listOfThree.iterator();
        iterator.next();
        iterator.remove();
        assertEquals(2, listOfThree.size());
    }

    @DisplayName("Next after remove returns the element following the removed")
    @Test
    public void testIteratorRemove_NextElementValue() {
        Iterator<String> iterator = listOfThree.iterator();
        iterator.next();
        iterator.remove();
        assertEquals("B", iterator.next());
    }

    @DisplayName("Remove when next() was never called - throws IllegalStateException")
    @Test
    public void testIteratorRemove_NoReads_IllegalState() {
        Iterator<String> iterator = listOfThree.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @DisplayName("Remove of already removed element - throws IllegalStateException")
    @Test
    public void testIteratorRemove_NoElement_IllegalState() {
        Iterator<String> iterator = listOfThree.iterator();
        iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }
}
