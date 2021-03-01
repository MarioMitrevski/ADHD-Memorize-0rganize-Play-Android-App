package com.example.myfirstapp.ui.content.calendar;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentAddTodoitemBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.List;

public class AddToDoItemFragment extends BottomSheetDialogFragment {

    interface AddToDoItemFragmentListener {
        void onActivityCreated();
    }

    private AddToDoItemFragmentListener listener = null;

    private FragmentAddTodoitemBinding binding;
    private ToDoItemIconsAdapter adapter;
    private AddToDoItemViewModel addToDoItemViewModel;

    public static AddToDoItemFragment create(
            String id,
            String title,
            String description,
            String selectedDate,
            String time,
            int imageUri
    ) {
        AddToDoItemFragment fragment = new AddToDoItemFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        args.putString("title", title);
        args.putString("description", description);
        args.putString("selectedDate", selectedDate);
        args.putString("time", time);
        args.putInt("imageUri", imageUri);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().setOnShowListener(dialog -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
            View bottomSheetInternal = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheetInternal).setState(BottomSheetBehavior.STATE_EXPANDED);
        });
        binding = FragmentAddTodoitemBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addToDoItemViewModel = new ViewModelProvider(this).get(AddToDoItemViewModel.class);

        setRecyclerView();
        setEditTextListeners();
        setTimeListener();

        String id = getArguments().getString("id");
        String title = getArguments().getString("title");
        String description = getArguments().getString("description");
        String date = getArguments().getString("selectedDate");
        String time = getArguments().getString("time");
        int imageUri = getArguments().getInt("imageUri");

        if (title != null && description != null && date != null && time != null && imageUri != -1) {
            binding.activityTitleEditTextView.setText(title);
            binding.activityDescriptionEditTextView.setText(description);
            binding.timeTextView.setText(time);
            adapter.setSelectedItem(imageUri);
            binding.recyclerView.scrollToPosition(adapter.getSelectedPosition());
            addToDoItemViewModel.setType(AddToDoItemType.EDIT);
            addToDoItemViewModel.setId(id);
        }

        binding.addActivityBtn.setOnClickListener(v -> addToDoItemViewModel.onSubmitClicked(binding.activityTitleEditTextView.getText().toString(),
                binding.activityDescriptionEditTextView.getText().toString(),
                binding.timeTextView.getText().toString(),
                adapter.getSelectedItem(),
                getArguments().getString("selectedDate")));


        addToDoItemViewModel.getSubmitButtonLiveData().observe(getViewLifecycleOwner(), addToDoItemState -> {

            if (addToDoItemState instanceof AddToDoItemState.OnSubmitClicked) {
                if (!((AddToDoItemState.OnSubmitClicked) addToDoItemState).enabled) {
                    Toast.makeText(requireContext(), "Пополнете ги сите полиња соодветно.", Toast.LENGTH_SHORT).show();
                }
            }
            if (addToDoItemState instanceof AddToDoItemState.SuccessfulCreation) {
                if (addToDoItemViewModel.getType() == AddToDoItemType.CREATE) {
                    Toast.makeText(requireContext(), "Успешно креирана активност.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "Успешно ажурирана активност.", Toast.LENGTH_SHORT).show();
                }
                listener.onActivityCreated();
                this.dismiss();
            }

        });
    }

    private void setRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        List<Integer> drawableIds = new ArrayList<>();
        drawableIds.add(R.drawable.ic_lunch_box);
        drawableIds.add(R.drawable.ic_fruits);
        drawableIds.add(R.drawable.ic_water);
        drawableIds.add(R.drawable.ic_homework);
        drawableIds.add(R.drawable.ic_friends);
        drawableIds.add(R.drawable.ic_exercise);
        drawableIds.add(R.drawable.ic_bus);
        drawableIds.add(R.drawable.ic_taxi);
        drawableIds.add(R.drawable.ic_plant);
        drawableIds.add(R.drawable.ic_zoo);
        drawableIds.add(R.drawable.ic_animals);
        drawableIds.add(R.drawable.ic_bug_spray);
        drawableIds.add(R.drawable.ic_grandparents);

        adapter = new ToDoItemIconsAdapter(drawableIds);
        binding.recyclerView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void setTimeListener() {
        binding.timeCard.setOnClickListener(v -> {

            MaterialTimePicker picker =
                    new MaterialTimePicker.Builder()
                            .setTimeFormat(TimeFormat.CLOCK_12H)
                            .setHour(12)
                            .setMinute(10)
                            .setTitleText("Одберете време за активноста")
                            .build();
            picker.show(getParentFragmentManager(), "tag");

            picker.addOnPositiveButtonClickListener(v1 -> {
                String time = picker.getHour() + ":" + picker.getMinute();
                addToDoItemViewModel.onTimeChanged(time);
                binding.timeTextView.setText(time);
            });
        });
    }

    private void setEditTextListeners() {
        binding.activityTitleEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addToDoItemViewModel.onActivityTitleChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.activityDescriptionEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addToDoItemViewModel.onActivityDescriptionChanged(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    void setAddToDoItemFragmentListener(AddToDoItemFragmentListener addToDoItemFragmentListener) {
        this.listener = addToDoItemFragmentListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}