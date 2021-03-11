package com.example.myfirstapp.ui.content.calendar;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.ui.content.calendar.network.ToDoItem;
import com.example.myfirstapp.ui.content.calendar.network.ToDoItemsCollection;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
                .filter(t -> t.getDate().equals(selectedDate.getDate().toString())).sorted((o1, o2) -> {
                    String startTime = o1.getTime();
                    String endTime = o2.getTime();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    Date d1 = null;
                    Date d2 = null;
                    try {
                        d1 = sdf.parse(startTime);
                        d2 = sdf.parse(endTime);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return (int) (d1.getTime() - d2.getTime());
                }).collect(Collectors.toList());
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
