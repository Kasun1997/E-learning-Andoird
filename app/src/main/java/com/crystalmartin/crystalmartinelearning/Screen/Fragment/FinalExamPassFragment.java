package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;

import com.crystalmartin.crystalmartinelearning.BottomNavigation.Main_Container;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront.View_model_finalexam_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.View_model_finalexam;
import com.crystalmartin.crystalmartinelearning.R;


public class FinalExamPassFragment extends Fragment {

    private View view;
    private TextView txvwScore, txvwStatus;
    private String totalQuestion, totalCorrection, moduleId, requiredAnswers;
    private AppCompatButton btnNext;
    private int totalPercentage = 0;
    private View_model_finalexam view_model_finalexam;
    private View_model_finalexam_history_back_front view_model_finalexam_history_back_front;
    private ImageView imgPassFail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_final_exam_pass, container, false);

        CancelOnBackPress();
        view_model_finalexam = new ViewModelProvider(requireActivity()).get(View_model_finalexam.class);
        view_model_finalexam_history_back_front = new ViewModelProvider(requireActivity()).get(View_model_finalexam_history_back_front.class);

        txvwScore = view.findViewById(R.id.txvwScore);
        btnNext = view.findViewById(R.id.btnNextd);
        txvwStatus = view.findViewById(R.id.txvwStatus);
        imgPassFail = view.findViewById(R.id.imgPassFail);

        Bundle bundle = this.getArguments();
        totalQuestion = bundle.getString("QuestionCount");
        totalCorrection = bundle.getString("TotalCorrection");
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
                view_model_finalexam.ClearHistory();
                view_model_finalexam_history_back_front.ClearHistory();
                MoveToHome();
            }
        });
        return view;
    }

    private void MoveToHome() {

        Intent fragIntent = new Intent(getActivity(), Main_Container.class);
        getActivity().startActivity(fragIntent);
        Log.e("SummeryFragment", "back");
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