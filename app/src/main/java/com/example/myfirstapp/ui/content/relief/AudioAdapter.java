package com.example.myfirstapp.ui.content.relief;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioItemViewHolder> {

    private List<AudioFile> audioFiles;


    public AudioAdapter(ArrayList<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
    }

    @NonNull
    @Override
    public AudioItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audio_item_layout, parent, false);

        return new AudioItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudioItemViewHolder holder, int position) {
        holder.getAudioTitleView().setText(audioFiles.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return audioFiles.size();
    }


    public static class AudioItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView audioImageView;
        private final TextView audioTitleView;

        public AudioItemViewHolder(@NonNull View itemView) {
            super(itemView);

            audioTitleView = (TextView) itemView.findViewById(R.id.audioTitleView);
            audioImageView = (ImageView) itemView.findViewById(R.id.audioImageView);
        }

        public TextView getAudioTitleView() {
            return audioTitleView;
        }

        public ImageView getAudioImageView() {
            return audioImageView;
        }
    }
}
