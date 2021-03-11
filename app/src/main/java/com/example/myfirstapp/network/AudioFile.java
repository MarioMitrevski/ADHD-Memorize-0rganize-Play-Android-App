package com.example.myfirstapp.network;


import java.io.Serializable;

public class AudioFile implements Serializable {

    private int id;
    private String title;
    private String artist;
    private int audioResource;
    private int imageResource;



    public AudioFile() {
    }

    public AudioFile(int id, String title, String artist, int audioResource, int imageResource) {
        this.id = id;
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

    public int getId() {
        return id;
    }
}
