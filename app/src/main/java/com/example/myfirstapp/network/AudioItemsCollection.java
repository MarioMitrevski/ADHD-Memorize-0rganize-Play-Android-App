package com.example.myfirstapp.network;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.List;

public class AudioItemsCollection {

    private List<AudioFile> audioFiles;

    public AudioItemsCollection() {
        this.audioFiles = new ArrayList<>();

        audioFiles.add(new AudioFile(0, "Enjoy the sunset", "Sunny Artists", R.raw.adhd_audio_1, R.drawable.relax_yoga_1));
        audioFiles.add(new AudioFile(1, "Chill wit the wind", "Wind Artists", R.raw.adhd_audio_2, R.drawable.ic_adhd_audio_cover2));
        audioFiles.add(new AudioFile(2, "Nature Queen", "LoFi HiGroup", R.raw.adhd_audio_3, R.drawable.ic_adhd_audio_cover3));
        audioFiles.add(new AudioFile(3, "Sound of the Sea", "Duke Dumont", R.raw.adhd_audio_4, R.drawable.ic_adhd_audio_cover4));
        audioFiles.add(new AudioFile(4, "Ocean Drive", "GrooveGroup", R.raw.adhd_audio_5, R.drawable.ic_adhd_audio_cover5));
        audioFiles.add(new AudioFile(5, "Here Comes the Sun", "The Beatles", R.raw.adhd_audio_6, R.drawable.ic_adhd_audio_cover6));
    }

    public List<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    public AudioFile getNextAudio(int currentAudioId) {
        if (currentAudioId + 1 < audioFiles.size()) {
            return audioFiles.get(currentAudioId + 1);
        } else {
            return null;
        }
    }

    public AudioFile getPreviousAudio(int currentAudioId) {
        if (currentAudioId > 0) {
            return audioFiles.get(currentAudioId - 1);
        } else {
            return null;
        }
    }
}
