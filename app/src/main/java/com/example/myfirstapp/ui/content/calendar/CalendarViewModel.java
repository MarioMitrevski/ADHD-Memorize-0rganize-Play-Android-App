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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(CalendarDay date) {
        if (selectedDate == null) {
            selectedDate = date;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<ToDoItem> getToDoItems() {
        return ToDoItemsCollection.getInstance().getToDoItems("1234")
                .stream()
                .filter(t -> t.getDate().equals(selectedDate.getDate().toString()))
                .collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void deleteToDoItem(ToDoItem toDoItem) {
        ToDoItemsCollection.getInstance().getToDoItems("1234").remove(toDoItem);
    }

    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setSelectedDate(CalendarDay newDate) {
        selectedDate = newDate;
    }
}
