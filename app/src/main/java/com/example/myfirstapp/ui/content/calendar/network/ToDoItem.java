package com.example.myfirstapp.ui.content.calendar.network;


import java.util.Objects;

public class ToDoItem {

    private String id;
    private int imageUri;
    private String title;
    private String description;
    private String time;
    private String date;

    public ToDoItem(String id, String title, String date, String time, int imageUri, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.title = title;
        this.imageUri = imageUri;
        this.description = description;
    }

    public ToDoItem(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDoItem toDoItem = (ToDoItem) o;
        return Objects.equals(id, toDoItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}