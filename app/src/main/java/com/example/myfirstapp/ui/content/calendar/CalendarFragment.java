package com.example.myfirstapp.ui.content.calendar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myfirstapp.databinding.FragmentCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import org.threeten.bp.DayOfWeek;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            adapter.updateDataSet(calendarViewModel.getToDoItems());

        });
        binding.todoRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new TodoAdapter(new ArrayList<>());
        binding.todoRecyclerView.setAdapter(adapter);

        binding.addActivityBtn.setOnClickListener(v -> {

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}