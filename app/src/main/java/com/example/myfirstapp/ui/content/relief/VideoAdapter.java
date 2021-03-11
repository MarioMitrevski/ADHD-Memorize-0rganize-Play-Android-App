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
import com.example.myfirstapp.ui.content.relief.network.VideoItem;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoItemViewHolder> {

    private List<VideoItem> videoItemList;
    private Consumer<VideoItem> onItemClicked;

    class VideoItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleView;
        private final TextView durationView;
        private final ImageView imageView;

        public VideoItemViewHolder(View view) {
            super(view);

            titleView = (TextView) view.findViewById(R.id.video_title);
            durationView = (TextView) view.findViewById(R.id.video_duration);
            imageView = (ImageView) view.findViewById(R.id.video_image);
        }

        void bind(VideoItem videoItem) {
            titleView.setText(videoItem.getTitle());
            durationView.setText(videoItem.getDuration());
            imageView.setImageResource(videoItem.getImageUri());

            itemView.setOnClickListener(v -> onItemClicked.accept(videoItem));
        }
    }

    public VideoAdapter(List<VideoItem> videoItems, Consumer<VideoItem> onItemClicked) {
        this.videoItemList = videoItems;
        this.onItemClicked = onItemClicked;
    }

    @NonNull
    @Override
    public VideoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.video_item_layout, viewGroup, false);

        return new VideoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoItemViewHolder viewHolder, final int position) {
        viewHolder.bind(videoItemList.get(position));

    }

    @Override
    public int getItemCount() {
        return videoItemList.size();
    }
}