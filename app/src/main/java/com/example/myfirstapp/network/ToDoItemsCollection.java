package com.example.myfirstapp.network;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ToDoItemsCollection {

    private static ToDoItemsCollection single_instance = null;

    private Map<String, List<ToDoItem>> toDoItemsMap;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ToDoItem> getToDoItems(String userId) {
        return toDoItemsMap.getOrDefault(userId, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private ToDoItemsCollection() {
        this.toDoItemsMap = new HashMap<>();

        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Напиши домашно", "2021-03-12", "12:00", R.drawable.ic_homework, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Играј си со другарите", "2021-03-12", "13:00", R.drawable.ic_friends, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Јади овошје", "2021-03-12", "14:00", R.drawable.ic_lunch_box, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Напиши домашно", "2021-03-12", "12:00", R.drawable.ic_homework, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Играј си со другарите", "2021-03-12", "13:00", R.drawable.ic_friends, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Јади овошје", "2021-03-12", "14:00", R.drawable.ic_lunch_box, "Опис за активноста"));

        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Напиши домашно", "2021-03-01", "12:00", R.drawable.ic_homework, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Играј си со другарите", "2021-03-01", "13:00", R.drawable.ic_friends, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Јади овошје", "2021-03-01", "14:00", R.drawable.ic_lunch_box, "Опис за активноста"));

        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Напиши домашно", "2021-02-24", "12:00", R.drawable.ic_homework, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Играј си со другарите Играј си со другарите Играј си со другарите", "2021-02-24", "13:00", R.drawable.ic_friends, "Опис за активноста"));
        toDoItems.add(new ToDoItem(UUID.randomUUID().toString(), "Јади овошје", "2021-02-24", "14:00", R.drawable.ic_lunch_box, "Опис за активноста"));
        toDoItemsMap.putIfAbsent("1234", toDoItems);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static ToDoItemsCollection getInstance() {
        if (single_instance == null)
            single_instance = new ToDoItemsCollection();

        return single_instance;
    }
}
