package com.example.myfirstapp.ui.content.calendar;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentCalendarBinding;
import com.example.myfirstapp.network.ToDoItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.TemporalAmount;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private CalendarViewModel calendarViewModel;
    private TodoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.toolbarText.setText("Календар на активности");
        binding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());

        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        calendarViewModel.init(CalendarDay.today());

        binding.calendarView.state().edit()
                .setFirstDayOfWeek(DayOfWeek.MONDAY)
                .setMinimumDate(calendarViewModel.getSelectedDate().getDate().minusDays(1))
                .setCalendarDisplayMode(CalendarMode.WEEKS)
                .setShowWeekDays(true)
                .commit();
        binding.calendarView.setSelectedDate(calendarViewModel.getSelectedDate());

        binding.calendarView.setOnDateChangedListener((widget, date, selected) -> {
            calendarViewModel.setSelectedDate(date);
            String text;
            if (date.getDate().compareTo(CalendarDay.today().getDate().minusDays(1)) == 0) {
                text = "Активности вчера";
            } else if (date.getDate().compareTo(CalendarDay.today().getDate()) == 0) {
                text = "Активности за денес";
            } else if (date.getDate().minusDays(1).equals(CalendarDay.today().getDate())) {
                text = "Активности за утре";
            } else {
                text = "Активности за " + date.getDate();
            }
            binding.activitiesForDayView.setText(text);
        });
        ArrayList<ToDoItem> toDoItems = new ArrayList<>();
        toDoItems.add(new ToDoItem("Напиши домашно", "12:00", R.drawable.ic_homework));
        toDoItems.add(new ToDoItem("Играј си со другарите", "13:00", R.drawable.ic_friends));
        toDoItems.add(new ToDoItem("Јади овошје", "14:00", R.drawable.ic_lunch_box));

        toDoItems.add(new ToDoItem("Напиши домашно", "12:00", R.drawable.ic_homework));
        toDoItems.add(new ToDoItem("Играј си со другарите", "13:00", R.drawable.ic_friends));
        toDoItems.add(new ToDoItem("Јади овошје", "14:00", R.drawable.ic_lunch_box));

        toDoItems.add(new ToDoItem("Напиши домашно", "12:00", R.drawable.ic_homework));
        toDoItems.add(new ToDoItem("Играј си со другарите", "13:00", R.drawable.ic_friends));
        toDoItems.add(new ToDoItem("Јади овошје", "14:00", R.drawable.ic_lunch_box));


        binding.todoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TodoAdapter(toDoItems);
        binding.todoRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}