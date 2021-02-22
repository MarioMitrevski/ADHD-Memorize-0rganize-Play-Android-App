package com.example.myfirstapp.network;


public class ToDoItem {

    String title;
    String time;
    int imageUri;
    String description;

    public ToDoItem(String title, String time, int imageUri) {
        this.time = time;
        this.title = title;
        this.imageUri = imageUri;
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
}