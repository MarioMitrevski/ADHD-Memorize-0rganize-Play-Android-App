package com.example.myfirstapp.ui.content.calendar;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.network.ToDoItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoItemViewHolder> {

    private List<ToDoItem> toDoItemsList;
    private Consumer<ToDoItem> onItemClicked;


    class ToDoItemViewHolder extends RecyclerView.ViewHolder {
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

        @RequiresApi(api = Build.VERSION_CODES.N)
        void bind(ToDoItem toDoItem) {
            titleView.setText(toDoItem.getTitle());
            timeView.setText(toDoItem.getTime());
            imageView.setImageResource(toDoItem.getImageUri());

            itemView.setOnClickListener(v -> onItemClicked.accept(toDoItem));
        }

    }

    public TodoAdapter(List<ToDoItem> toDoItems, Consumer<ToDoItem> onItemClicked) {
        toDoItemsList = toDoItems;
        this.onItemClicked = onItemClicked;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateDataSet(List<ToDoItem> toDoItemList) {
        this.toDoItemsList = toDoItemList;
        notifyDataSetChanged();
    }

    public void deleteToDoItem(ToDoItem toDoItem) {
        toDoItemsList.remove(toDoItem);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ToDoItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.to_do_item_layout, viewGroup, false);

        return new ToDoItemViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(ToDoItemViewHolder viewHolder, final int position) {
        viewHolder.bind(toDoItemsList.get(position));

    }

    @Override
    public int getItemCount() {
        return toDoItemsList.size();
    }
}
