package com.example.myfirstapp.ui.content.relief;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.ActivityPlayerBinding;


public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding;
    static MediaPlayer mediaPlayer;
    AudioFile audioFile;
    private Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;
    Thread playThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlayerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getAudioFile();
        binding.durationAudioBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            if (mediaPlayer != null) {
                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                binding.durationAudioBar.setProgress(mCurrentPosition);
                binding.durationAudioPlayed.setText(formattedTime(mCurrentPosition));
            }
        }, delay);

        binding.backBtn.setOnClickListener(v -> {
            finish();
        });


    }

    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

    private void getAudioFile() {
        if (getIntent().getExtras() != null) {
            audioFile = (AudioFile) getIntent().getSerializableExtra("audioFile");
            setViews(audioFile);
            binding.playPauseBtn.setImageResource(R.drawable.ic_pause_audio);
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = MediaPlayer.create(this, audioFile.getAudioResource());
                mediaPlayer.start();
            } else {
                mediaPlayer = MediaPlayer.create(this, audioFile.getAudioResource());
                mediaPlayer.start();
            }
            int totalDuration = mediaPlayer.getDuration() / 1000;
            binding.durationAudioBar.setMax(totalDuration);
            binding.totalAudioDuration.setText(formattedTime(totalDuration));


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.playPauseBtn.setOnClickListener(v -> {
            playPauseBtnClicked();
        });
    }

    private void playPauseBtnClicked() {

        if(mediaPlayer.isPlaying()){
            binding.playPauseBtn.setImageResource(R.drawable.ic_play_audio);
            mediaPlayer.pause();
        }else{
            binding.playPauseBtn.setImageResource(R.drawable.ic_pause_audio);
            mediaPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void setViews(AudioFile audioFile) {
        binding.audioArtist.setText(audioFile.getArtist());
        binding.audioTitle.setText(audioFile.getTitle());
        binding.audioCoverArt.setImageResource(audioFile.getImageResource());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        binding = null;
    }
}