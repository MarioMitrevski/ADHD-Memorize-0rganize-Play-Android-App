package com.example.myfirstapp.ui.content.calendar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.network.ToDoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoItemViewHolder> {

    private ArrayList<ToDoItem> toDoItemsList;

    public static class ToDoItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeView;
        private final TextView titleView;
        private final ImageView imageView;

        public ToDoItemViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            titleView = (TextView) view.findViewById(R.id.titleView);
            timeView = (TextView) view.findViewById(R.id.timeView);
            imageView = (ImageView) view.findViewById(R.id.imageView);

        }

        public TextView getTitleView() {
            return titleView;
        }

        public TextView getTimeView() {
            return timeView;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }

    public TodoAdapter(ArrayList<ToDoItem> toDoItems) {
        toDoItemsList = toDoItems;
    }

    @Override
    public ToDoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.to_do_item_layout, viewGroup, false);

        return new ToDoItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ToDoItemViewHolder viewHolder, final int position) {

        viewHolder.getTitleView().setText(toDoItemsList.get(position).getTitle());
        viewHolder.getTimeView().setText(toDoItemsList.get(position).getTime());
        Picasso.get().load(toDoItemsList.get(position).getImageUri()).fit().into(viewHolder.getImageView());
    }

    @Override
    public int getItemCount() {
        return toDoItemsList.size();
    }
}
