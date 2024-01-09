package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront.View_model_quiz_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.View_model_quiz_history;
import com.crystalmartin.crystalmartinelearning.R;


public class QuizPassFragment extends Fragment {

    private View view;
    private TextView txvwScore, txvwStatus;
    private String totalQuestion, totalCorrection, moduleId, requiredAnswers;
    private AppCompatButton btnNext;
    private int totalPercentage = 0;
    private View_model_quiz_history viewModelQuizHistory;
    private View_model_quiz_history_back_front viewModelQuizHistoryBackFront;
    private ImageView imgPassFail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quiz_pass, container, false);

        CancelOnBackPress();
        viewModelQuizHistory = new ViewModelProvider(requireActivity()).get(View_model_quiz_history.class);
        viewModelQuizHistoryBackFront = new ViewModelProvider(requireActivity()).get(View_model_quiz_history_back_front.class);

        txvwScore = view.findViewById(R.id.txvwScore);
        btnNext = view.findViewById(R.id.btnNext);
        txvwStatus = view.findViewById(R.id.txvwStatus);
        imgPassFail=view.findViewById(R.id.imgPassFail);

        Bundle bundle = this.getArguments();
        totalQuestion = bundle.getString("QuestionCount");
        totalCorrection = bundle.getString("TotalCorrection");
        moduleId = bundle.getString("moduleID");
        requiredAnswers = bundle.getString("RequiredAnswers");
        Log.e("CHECK_ALL_ID", requiredAnswers + "//" + moduleId);

        double a = Integer.parseInt(totalQuestion);
        double b = Integer.parseInt(totalCorrection);
        double c = (b / a) * 100;

        Log.e("QuizPassFragment", totalCorrection + "//" + totalQuestion + "///" + c);
        Log.e("QuizPassFragment", "1 : " + a + "//" + b);

        int tot = (int) Double.parseDouble(String.valueOf(c));
        txvwScore.setText(tot + "%");

        double rqs = Double.parseDouble(requiredAnswers);
        if (rqs <= b) {
            Log.e("PASS_FAIL", "pass");
            txvwStatus.setText("PASS");
        } else {
            Log.e("PASS_FAIL", "fail");
            txvwStatus.setText("FAIL");
            txvwStatus.setTextColor(ContextCompat.getColor(getContext(), R.color.crystalRed));
            imgPassFail.setImageResource(R.drawable.quiz_fail_icon);
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModelQuizHistory.ClearHistory();
                viewModelQuizHistoryBackFront.ClearHistory();
                MoveToFeedBack();
            }
        });

        return view;
    }

    private void MoveToFeedBack() {
        Bundle bundle = new Bundle();
        bundle.putString("moduleID", moduleId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new RatingFragment();
        mFrag.setArguments(bundle);
        t.replace(R.id.frame, mFrag);
        t.addToBackStack(null);
        t.commit();
    }

    private void CancelOnBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.e("SummeryFragment", "back");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

}