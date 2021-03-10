package com.example.myfirstapp.ui.content.relief.network;

public class VideoItem {

    private String title;
    private String duration;
    private int imageUri;
    private String url;

    public VideoItem(String title, String duration, int imageUri, String url) {
        this.duration = duration;
        this.title = title;
        this.imageUri = imageUri;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getImageUri() {
        return imageUri;
    }

    public void setImageUri(int imageUri) {
        this.imageUri = imageUri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}