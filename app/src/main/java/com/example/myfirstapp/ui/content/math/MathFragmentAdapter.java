package com.example.myfirstapp.ui.content.math;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;

import java.util.ArrayList;

public class MathFragmentAdapter extends RecyclerView.Adapter<MathFragmentAdapter.MathAnswerViewHolder> {
    public interface OnSuggestedAnswerClickListener {
        void onAnswerClick(String answer, View itemView);
    }

    ArrayList<String> suggestedAnswersList;
    OnSuggestedAnswerClickListener listener;
    String correctAnswer;
    public static boolean isClickable = true;

    public MathFragmentAdapter(ArrayList<String> suggestedAnswersList, String correctAnswer,
                               OnSuggestedAnswerClickListener listener) {
        this.suggestedAnswersList = suggestedAnswersList;
        this.listener = listener;
        this.correctAnswer = correctAnswer;
    }

    @NonNull
    @Override
    public MathAnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_button_answer, parent, false);
        return new MathFragmentAdapter.MathAnswerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MathAnswerViewHolder holder, int position) {
        holder.bind(suggestedAnswersList.get(position), listener, correctAnswer);
    }

    @Override
    public int getItemCount() {
        return suggestedAnswersList.size();
    }

    public static class MathAnswerViewHolder extends RecyclerView.ViewHolder {
        Button suggestedAnswerButton;

        public MathAnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            suggestedAnswerButton = itemView.findViewById(R.id.button_view_holder_answer);
        }

        public void bind(String suggestedAnswer, OnSuggestedAnswerClickListener listener,
                         String correctAnswer) {
            suggestedAnswerButton.setBackgroundColor(Color.TRANSPARENT);
            suggestedAnswerButton.setText(suggestedAnswer);
            suggestedAnswerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isClickable) {
                        listener.onAnswerClick(suggestedAnswer, v);
                    }
                }
            });
        }
    }
}
