package ua.yelisieiev.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.yelisieiev.implementations.MyArrayList;
import ua.yelisieiev.interfaces.List;

import static org.junit.jupiter.api.Assertions.*;

public class MyArrayListTest extends AbstractListTest {

    @Override
    public List<String> createList() {
        return new MyArrayList<>();
    }

    @DisplayName("Internal array grows automatically")
    @Test
    public void testInternalArrayGrow () {
        MyArrayList<String> growingList = new MyArrayList<>(4);
        growingList.add("A");
        growingList.add("B");
        growingList.add("C");
        growingList.add("D");
        // should grow now
        growingList.add("G");
        assertEquals("G", growingList.get(growingList.size() - 1));
    }
}
