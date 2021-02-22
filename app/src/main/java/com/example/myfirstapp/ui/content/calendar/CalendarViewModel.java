package com.example.myfirstapp.ui.content.calendar;

import androidx.lifecycle.ViewModel;

import com.prolificinteractive.materialcalendarview.CalendarDay;

public class CalendarViewModel extends ViewModel {

    CalendarDay selectedDate;

    public void init(CalendarDay date) {
        if (selectedDate == null) {
            selectedDate = date;
        }
    }

    public CalendarDay getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(CalendarDay newDate) {
        selectedDate = newDate;
    }
}
