package com.example.myfirstapp.content.math;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentMathBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.myfirstapp.ConstantValuesUtil.CLOSE_RESULT_LABEL;
import static com.example.myfirstapp.ConstantValuesUtil.NUMBER_OF_QUESTIONS;
import static com.example.myfirstapp.ConstantValuesUtil.STARTING_GAME_POINTS;
import static com.example.myfirstapp.ConstantValuesUtil.STARTING_QUESTION_NUMBER;

public class MathFragment extends Fragment {
    public interface OnQuizItemClickedListener {
        void updateStarted();

        void updateEnded();
    }

    private FragmentMathBinding mathBinding;
    ArrayList<String> suggestedAnswersList;
    MathFragmentAdapter mathFragmentAdapter;
    String correctAnswer;
    int questionCounter = STARTING_QUESTION_NUMBER;
    int points = STARTING_GAME_POINTS;
    NavController navController;
    OnQuizItemClickedListener itemClickedListener;
    int userCurrentPointState;
    DatabaseReference pointsRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mathBinding = FragmentMathBinding.inflate(inflater, container, false);
        return mathBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startAnimationCountDown();
        initAdapter();
        setBackButtonListener();
        readCurrentUserPointState();
    }

    private void readCurrentUserPointState() {
        pointsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userCurrentPointState = Integer.parseInt(String.valueOf(snapshot.getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setBackButtonListener() {
        mathBinding.imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_mathFragment_to_contentFragment);
                itemClickedListener.updateEnded();
            }
        });
    }

    private void startAnimationCountDown() {
        suggestedAnswersList = new ArrayList<>();
        questionCounter = 1;
        mathBinding.lottieanimationviewGameCounter.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                itemClickedListener.updateEnded();
                readQuestion();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                changeQuestion();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void readQuestion() {
        DatabaseReference gameMathQuestionsRef = FirebaseDatabase.getInstance().getReference()
                .child("Math Game").child(String.valueOf(questionCounter));
        gameMathQuestionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (Objects.requireNonNull(child.getKey()).matches("[abcd]")) {
                        suggestedAnswersList.add((String) child.getValue());
                        mathFragmentAdapter.notifyItemInserted(suggestedAnswersList.size() - 1);
                    } else if (child.getKey().equals("question")) {
                        if (isVisible())
                            mathBinding.textViewQuestion.setText((String) child.getValue());
                    } else
                        correctAnswer = (String) child.getValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void changeQuestion() {
        questionCounter++;
        mathBinding.textViewQuestionCounter.setText(String.format("%s%s", getString(R.string
                .question_counter_prefix), questionCounter));
        if (questionCounter <= NUMBER_OF_QUESTIONS) {
            suggestedAnswersList.clear();
            mathFragmentAdapter.notifyDataSetChanged();
            mathBinding.lottieanimationviewGameCounter.playAnimation();
        } else {
            pointsRef.setValue(String.valueOf(userCurrentPointState + points));
            showResultDialog();
            navController.navigate(R.id.action_mathFragment_to_contentFragment);
            itemClickedListener.updateEnded();
        }
    }

    private void showResultDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.result_dialog_title))
                .setMessage("Петко Петковски ти освои:" + points)
                .setNegativeButton(CLOSE_RESULT_LABEL, null)
                .setIcon(R.drawable.ic_cubes_stack)
                .show();
    }

    private void initAdapter() {
        mathBinding.textViewUserPoints.setText(String.format("%s%s", getString(R.string
                .points_prefix), points));
        mathFragmentAdapter = new MathFragmentAdapter(suggestedAnswersList, correctAnswer,
                new MathFragmentAdapter.OnSuggestedAnswerClickListener() {
                    @Override
                    public void onAnswerClick(String answer, View itemView) {
                        if (answer.equals(correctAnswer)) {
                            mathBinding.textViewUserPoints.setText(String.format("%s%s",
                                    getString(R.string.points_prefix), ++points));
                            itemView.setBackgroundColor(Color.GREEN);
                            Toast.makeText(getActivity(), "ТОЧНО!", Toast.LENGTH_SHORT).show();
                        } else {
                            itemView.setBackgroundColor(Color.RED);
                            mathBinding.textViewUserPoints.setText(String.format("%s%s",
                                    getString(R.string.points_prefix), --points));
                            Toast.makeText(getActivity(), "НЕТОЧНО!", Toast.LENGTH_SHORT).show();
                        }
                        itemClickedListener.updateStarted();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                changeQuestion();
                            }
                        }, 1000);
                    }
                });
        mathBinding.recyclerViewMath.setAdapter(mathFragmentAdapter);
        navController = NavHostFragment.findNavController(this);
        mathBinding.textViewQuestionCounter.setText(String.format("%s%s", getString(R.string
                .question_counter_prefix), questionCounter));
        pointsRef = FirebaseDatabase.getInstance().getReference("users")
                .child("points");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MathFragment.OnQuizItemClickedListener) {
            itemClickedListener = (OnQuizItemClickedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement ProfileListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemClickedListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mathBinding = null;
    }
}
