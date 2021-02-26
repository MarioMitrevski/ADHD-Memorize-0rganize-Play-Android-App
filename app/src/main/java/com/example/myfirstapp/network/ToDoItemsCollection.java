package com.example.myfirstapp.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToDoItemsCollection {

    private Map<String, List<ToDoItem>> toDoItemsMap;

    public ToDoItemsCollection() {
        this.toDoItemsMap = new HashMap<>();
    }

    public Map<String, List<ToDoItem>> getToDoItemsMap() {
        return toDoItemsMap;
    }
}
