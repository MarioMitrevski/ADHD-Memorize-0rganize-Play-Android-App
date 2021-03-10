package com.example.myfirstapp.ui.content.math;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myfirstapp.R;
import com.example.myfirstapp.databinding.FragmentMathBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.myfirstapp.ConstantValuesUtil.CLOSE_RESULT_LABEL;
import static com.example.myfirstapp.ConstantValuesUtil.STARTING_GAME_POINTS;
import static com.example.myfirstapp.ConstantValuesUtil.STARTING_QUESTION_NUMBER;

public class MathFragment extends Fragment {

    private FragmentMathBinding mathBinding;
    ArrayList<String> suggestedAnswersList;
    MathFragmentAdapter mathFragmentAdapter;
    String correctAnswer;
    int questionCounter = STARTING_QUESTION_NUMBER;
    int points = STARTING_GAME_POINTS;
    NavController navController;
    int userCurrentPointState;
    DatabaseReference pointsRef;
    DatabaseReference gameMathRef;
    int numberOfQuestions;
    FirebaseUser currentUser;
    String nickname;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mathBinding = FragmentMathBinding.inflate(inflater, container, false);
        return mathBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mathBinding.toolbarText.setText(R.string.maths_game_toolbar_text);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences =
                getActivity().getSharedPreferences("com.example.myfirstapp.userstore", Context.MODE_PRIVATE);
        getCurrentUserNickname();
        initAdapter();
        showStartingDialog();
        setBackButtonListener();
        readCurrentUserPointState();
        startAnimationCountDown();
    }

    private void getCurrentUserNickname() {
        DatabaseReference nicknameRef =
                pointsRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid())
                        .child("nickname");
        nicknameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nickname = String.valueOf(snapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showStartingDialog() {
        final CharSequence[] items = {"Средно", "Тешко"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(R.string.starting_dialog_level_maths_game);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    gameMathRef = FirebaseDatabase.getInstance().getReference()
                            .child("Math Game");
                } else {
                    gameMathRef = FirebaseDatabase.getInstance().getReference()
                            .child("Math Game Advanced");
                }
                getNumberOfQuestions(gameMathRef);
            }
        });
        AlertDialog alert = builder.create();
        alert.getListView().setDivider(new ColorDrawable(Color.LTGRAY));
        alert.getListView().setDividerHeight(2);
        alert.show();
        alert.setCancelable(false);
    }

    private void getNumberOfQuestions(DatabaseReference gameMathRef) {
        gameMathRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                numberOfQuestions = (int) snapshot.getChildrenCount();
                mathBinding.lottieanimationviewGameCounter.playAnimation();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        mathBinding.backBtn.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    private void startAnimationCountDown() {
        questionCounter = 1;
        mathBinding.lottieanimationviewGameCounter.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
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
        DatabaseReference gameMathQuestionsRef = gameMathRef.child(String.valueOf(questionCounter));
        gameMathQuestionsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (Objects.requireNonNull(child.getKey()).matches("[abcd]")) {
                        suggestedAnswersList.add((String) child.getValue());
                        mathFragmentAdapter.notifyItemInserted(suggestedAnswersList.size() - 1);
                    } else if (child.getKey().equals("question")) {
                        if (isVisible()) {
                            mathBinding.textViewQuestion.setText((String) child.getValue());
                        }
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
        MathFragmentAdapter.isClickable = true;
        if (!isVisible())
            return;
        mathBinding.textViewQuestionCounter.setText(String.format("%s%s", getString(R.string
                .question_counter_prefix), questionCounter));
        if (questionCounter <= numberOfQuestions) {
            suggestedAnswersList.clear();
            mathFragmentAdapter.notifyDataSetChanged();
            mathBinding.lottieanimationviewGameCounter.playAnimation();
        } else {
            pointsRef.setValue(String.valueOf(userCurrentPointState + points));
            showResultDialog();
            requireActivity().onBackPressed();
        }
    }

    private void showResultDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.result_dialog_title))
                .setMessage(nickname + ", бројот на поени што ги освои е: " + points)
                .setNegativeButton(CLOSE_RESULT_LABEL, null)
                .setIcon(R.drawable.ic_cubes_stack)
                .show();
    }

    private void setAnswersFeedback(String answer, View itemView) {
        MediaPlayer mediaPlayer;
        if (answer.equals(correctAnswer)) {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.correct_answer_sound);
            mediaPlayer.start();
            mathBinding.textViewUserPoints.setText(String.format("%s%s",
                    getString(R.string.points_prefix), ++points));
            itemView.setBackgroundColor(Color.GREEN);
        } else {
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.bad_answer_sound);
            mediaPlayer.start();
            itemView.setBackgroundColor(Color.RED);
            mathBinding.textViewUserPoints.setText(String.format("%s%s",
                    getString(R.string.points_prefix), --points));
        }
    }

    private void initAdapter() {
        suggestedAnswersList = new ArrayList<>();
        mathBinding.textViewUserPoints.setText(String.format("%s%s", getString(R.string
                .points_prefix), points));
        mathFragmentAdapter = new MathFragmentAdapter(suggestedAnswersList, correctAnswer,
                new MathFragmentAdapter.OnSuggestedAnswerClickListener() {
                    @Override
                    public void onAnswerClick(String answer, View itemView) {
                        MathFragmentAdapter.isClickable = false;
                        setAnswersFeedback(answer, itemView);
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
        pointsRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid())
                .child("points");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mathBinding = null;
    }
}
