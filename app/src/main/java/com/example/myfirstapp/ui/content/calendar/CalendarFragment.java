package com.example.myfirstapp.ui.content.calendar;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentCalendarBinding;
import com.example.myfirstapp.ui.content.calendar.network.ToDoItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;

import org.threeten.bp.DayOfWeek;

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
        adapter = new TodoAdapter(calendarViewModel.getToDoItems(), toDoItem -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(requireActivity());

            View dialogView = requireActivity().getLayoutInflater().inflate(
                    R.layout.fragment_todoitem_details,
                    null
            );
            dialogBuilder.setView(dialogView);
            adapter.notifyDataSetChanged();


            View cancelButton =
                    dialogView.findViewById(R.id.cancel_button);
            View editButton =
                    dialogView.findViewById(R.id.edit_button);
            View deleteButton =
                    dialogView.findViewById(R.id.delete_button);

            TextView titleTextView =
                    dialogView.findViewById(R.id.todoitem_title_text_view);
            TextView descriptionTextView =
                    dialogView.findViewById(R.id.todoitem_description_text_view);
            TextView timeTextView =
                    dialogView.findViewById(R.id.time_text_view);
            TextView dateTextView =
                    dialogView.findViewById(R.id.date_text_view);

            titleTextView.setText(toDoItem.getTitle());
            descriptionTextView.setText(toDoItem.getDescription());
            timeTextView.setText(toDoItem.getTime());
            dateTextView.setText(toDoItem.getDate());

            AlertDialog alertDialog = dialogBuilder.create();

            cancelButton.setOnClickListener(v -> alertDialog.dismiss());

            editButton.setOnClickListener(v -> {
                showBottomSheetDialog(toDoItem);
                alertDialog.dismiss();
            });
            deleteButton.setOnClickListener(v -> {
                calendarViewModel.deleteToDoItem(toDoItem);
                adapter.deleteToDoItem(toDoItem);
                alertDialog.dismiss();
            });
            alertDialog.show();
        });
        binding.todoRecyclerView.setAdapter(adapter);

        binding.addActivityBtn.setOnClickListener(v -> {
            showBottomSheetDialog(null);
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showBottomSheetDialog(ToDoItem toDoItem) {
        AddToDoItemFragment fragment;
        if (toDoItem != null) {
            fragment = AddToDoItemFragment.create(toDoItem.getId(),
                    toDoItem.getTitle(),
                    toDoItem.getDescription(),
                    calendarViewModel.selectedDate.getDate().toString(),
                    toDoItem.getTime(),
                    toDoItem.getImageUri());

        } else {
            fragment = AddToDoItemFragment.create(null, null, null,
                    calendarViewModel.selectedDate.getDate().toString(), null, -1);

        }
        fragment.setAddToDoItemFragmentListener(() -> adapter.updateDataSet(calendarViewModel.getToDoItems()));
        fragment.show(
                getParentFragmentManager(),
                "ADD_ACTIVITY_FRAGMENT_TAG"
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}