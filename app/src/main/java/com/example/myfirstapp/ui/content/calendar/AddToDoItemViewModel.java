package com.example.myfirstapp.ui.content.calendar;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.ui.content.calendar.network.ToDoItem;
import com.example.myfirstapp.ui.content.calendar.network.ToDoItemsCollection;

import java.util.List;
import java.util.UUID;


public class AddToDoItemViewModel extends ViewModel {

    private AddToDoItemType type = AddToDoItemType.CREATE;
    private String toDoItemId;

    private AddToDoItemState.ValidationErrors validationErrors = new AddToDoItemState.ValidationErrors();

    private MutableLiveData<AddToDoItemState> submitButtonLiveData;

    public MutableLiveData<AddToDoItemState> getSubmitButtonLiveData() {
        if (submitButtonLiveData == null) {
            submitButtonLiveData = new MutableLiveData<AddToDoItemState>();
        }
        return submitButtonLiveData;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSubmitClicked(String activityTitle, String description, String time, int selectedIconDrawableId, String selectedDate) {
        if ((activityTitle.isEmpty() || description.isEmpty() || time.split(":").length < 2)) {
            submitButtonLiveData.setValue(new AddToDoItemState.OnSubmitClicked(false));
        } else {

            ToDoItem toDoItem;
            if (type == AddToDoItemType.CREATE) {
                toDoItem = new ToDoItem(UUID.randomUUID().toString(), activityTitle, selectedDate, time, selectedIconDrawableId, description);
                ToDoItemsCollection.getInstance().getToDoItems("1234").add(toDoItem);
            } else {
                toDoItem = new ToDoItem(toDoItemId, activityTitle, selectedDate, time, selectedIconDrawableId, description);

                List<ToDoItem> toDoItemList = ToDoItemsCollection.getInstance().getToDoItems("1234");
                toDoItemList.set(toDoItemList.indexOf(toDoItem), toDoItem);
            }
            submitButtonLiveData.setValue(new AddToDoItemState.SuccessfulCreation());

        }
    }

    public void onActivityDescriptionChanged(String text) {
        validationErrors.isDescriptionEmpty = text.isEmpty();
    }

    public void onActivityTitleChanged(String text) {
        validationErrors.isTitleEmpty = text.isEmpty();
    }

    public void onTimeChanged(String text) {
        validationErrors.isTimeEmpty = text.isEmpty();
    }

    public void setType(AddToDoItemType type) {
        this.type = type;
    }

    public void setId(String id) {
        this.toDoItemId = id;
    }

    public AddToDoItemType getType() {
        return type;
    }
}

class AddToDoItemState {
    static class SuccessfulCreation extends AddToDoItemState {
    }

    static class OnSubmitClicked extends AddToDoItemState {
        Boolean enabled;

        public OnSubmitClicked(Boolean enabled) {
            this.enabled = enabled;
        }
    }

    static class ValidationErrors extends AddToDoItemState {

        boolean isTitleEmpty;
        boolean isDescriptionEmpty;
        boolean isTimeEmpty;

        public ValidationErrors() {

            this.isTitleEmpty = false;
            this.isDescriptionEmpty = false;
            this.isTimeEmpty = false;
        }
    }
}

enum AddToDoItemType {
    CREATE,
    EDIT
}