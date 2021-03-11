package com.example.myfirstapp.ui.content.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import java.util.List;

public class ToDoItemIconsAdapter extends RecyclerView.Adapter<ToDoItemIconsAdapter.ViewHolder> {

    private List<Integer> drawableIds;
    private int selectedPosition;

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView activityIcon;

        public ViewHolder(View view) {
            super(view);
            activityIcon = (ImageView) view.findViewById(R.id.activity_icon_image_view);
        }

        public void bind(Integer drawableId) {
            activityIcon.setImageResource(drawableId);
            if (selectedPosition == getAdapterPosition()) {
                activityIcon.setBackgroundResource(R.drawable.selected_item_background);
            } else {
                activityIcon.setBackgroundResource(android.R.color.transparent);
            }

            itemView.setOnClickListener(v -> {
                if (selectedPosition != getAdapterPosition()) {
                    notifyItemChanged(selectedPosition);
                    selectedPosition = getAdapterPosition();
                    notifyItemChanged(selectedPosition);

                }
            });
        }

    }

    public ToDoItemIconsAdapter(List<Integer> drawableIds) {
        this.drawableIds = drawableIds;
    }

    public void updateDataSet(List<Integer> drawableIds) {
        this.drawableIds = drawableIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.todoitem_icon_drawable_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.bind(drawableIds.get(position));
    }

    @Override
    public int getItemCount() {
        return drawableIds.size();
    }


    public Integer getSelectedItem() {
        return drawableIds.get(selectedPosition);
    }

    public Integer getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedItem(Integer imageUri) {
        notifyItemChanged(selectedPosition);
        selectedPosition = this.drawableIds.indexOf(imageUri);
        notifyItemChanged(selectedPosition);
    }

}

