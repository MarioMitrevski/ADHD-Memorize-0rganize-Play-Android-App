package com.example.myfirstapp.ui.content.relief;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import java.util.ArrayList;
import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.AudioItemViewHolder> {

    private List<AudioFile> audioFiles;
    private Consumer<AudioFile> onItemClicked;

    public AudioAdapter(ArrayList<AudioFile> audioFiles, Consumer<AudioFile> onItemClicked) {
        this.audioFiles = audioFiles;
        this.onItemClicked = onItemClicked;
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
        holder.bind(audioFiles.get(position));
    }

    @Override
    public int getItemCount() {
        return audioFiles.size();
    }


    class AudioItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView audioImageView;
        private final TextView audioTitleView;

        public AudioItemViewHolder(@NonNull View itemView) {
            super(itemView);

            audioTitleView = (TextView) itemView.findViewById(R.id.audioTitleView);
            audioImageView = (ImageView) itemView.findViewById(R.id.audioImageView);
        }

        void bind(AudioFile audioFile) {
            audioTitleView.setText(audioFile.getTitle());
            audioImageView.setImageResource(audioFile.getImageResource());

            itemView.setOnClickListener(v -> onItemClicked.accept(audioFile));
        }
    }
}