package ua.yelisieiev.tests;

import ua.yelisieiev.implementations.MyLinkedList;
import ua.yelisieiev.interfaces.List;

public class MyLinkedListTest extends AbstractListTest {
    @Override
    protected List<String> createList() {
        return new MyLinkedList<>();
    }


}
