package com.example.myfirstapp.ui.content.calendar;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.R;
import com.example.myfirstapp.network.ToDoItem;
import com.example.myfirstapp.network.ToDoItemsCollection;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class CalendarViewModel extends ViewModel {

    CalendarDay selectedDate;
    ToDoItemsCollection toDoItemsCollection;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(CalendarDay date) {
        if (selectedDate == null) {
            selectedDate = date;
        }
        toDoItemsCollection = new ToDoItemsCollection();
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        toDoItems.add(new ToDoItem("Напиши домашно", "2021-03-12", "12:00", R.drawable.ic_homework, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Играј си со другарите", "2021-03-12", "13:00", R.drawable.ic_friends, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Јади овошје", "2021-03-12", "14:00", R.drawable.ic_lunch_box, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Напиши домашно", "2021-03-12", "12:00", R.drawable.ic_homework, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Играј си со другарите", "2021-03-12", "13:00", R.drawable.ic_friends, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Јади овошје", "2021-03-12", "14:00", R.drawable.ic_lunch_box, new ArrayList<>()));

        toDoItems.add(new ToDoItem("Напиши домашно", "2021-03-01", "12:00", R.drawable.ic_homework, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Играј си со другарите", "2021-03-01", "13:00", R.drawable.ic_friends, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Јади овошје", "2021-03-01", "14:00", R.drawable.ic_lunch_box, new ArrayList<>()));

        toDoItems.add(new ToDoItem("Напиши домашно", "2021-02-24", "12:00", R.drawable.ic_homework, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Играј си со другарите Играј си со другарите Играј си со другарите", "2021-02-24", "13:00", R.drawable.ic_friends, new ArrayList<>()));
        toDoItems.add(new ToDoItem("Јади овошје", "2021-02-24", "14:00", R.drawable.ic_lunch_box, new ArrayList<>()));
        toDoItemsCollection.getToDoItemsMap().putIfAbsent("1234", toDoItems);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ToDoItem> getToDoItems() {
        return toDoItemsCollection.getToDoItemsMap()
                .getOrDefault("1234", new ArrayList<>())
                .stream()
                .filter(t -> t.getDate().equals(selectedDate.getDate().toString()))
                .collect(Collectors.toList());
    }

    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(CalendarDay newDate) {
        selectedDate = newDate;
    }
}
