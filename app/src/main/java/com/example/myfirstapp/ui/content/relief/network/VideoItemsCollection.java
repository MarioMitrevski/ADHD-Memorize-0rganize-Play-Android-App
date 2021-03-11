package com.example.myfirstapp.ui.content.relief.network;

import java.util.ArrayList;
import java.util.List;

public class VideoItemsCollection {

    private List<VideoItem> videoItems;

    public VideoItemsCollection() {
        this.videoItems = new ArrayList<>();
    }

    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }
}
