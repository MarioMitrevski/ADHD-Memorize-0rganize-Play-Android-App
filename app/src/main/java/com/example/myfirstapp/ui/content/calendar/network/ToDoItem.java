package com.example.myfirstapp.ui.content.calendar.network;


import java.util.List;

public class ToDoItem {

    private String title;
    private String time;
    private String date;
    private int imageUri;
    private String description;
    private List<String> stepsList;

    public ToDoItem(String title, String date, String time, int imageUri, List<String> stepsList) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.imageUri = imageUri;
        this.stepsList = stepsList;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public int getImageUri() {
        return imageUri;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public List<String> getStepsList() {
        return stepsList;
    }
}