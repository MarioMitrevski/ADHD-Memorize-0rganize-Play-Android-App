package com.example.myfirstapp.ui.content.relief;


import java.io.Serializable;

public class AudioFile implements Serializable {

    private String title;
    private String artist;
    private int audioResource;
    private int imageResource;



    public AudioFile() {
    }

    public AudioFile(String title, String artist, int audioResource, int imageResource) {
        this.title = title;
        this.artist = artist;
        this.audioResource = audioResource;
        this.imageResource = imageResource;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getAudioResource() {
        return audioResource;
    }

    public void setAudioResource(int audioResource) {
        this.audioResource = audioResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
