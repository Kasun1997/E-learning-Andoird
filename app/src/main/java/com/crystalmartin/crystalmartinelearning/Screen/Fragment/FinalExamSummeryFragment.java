package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Final_Exam_Summery;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.Model_final_exam_history;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.View_model_finalexam;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAttemptUpdate_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinalExamSummeryFragment extends Fragment {

    private View view;
    private String finalExameId, attemptRawId, requiredAnswers, questionCount;
    private View_model_finalexam finalexamViewModel;
    private RecyclerView recyclerView;
    private AppCompatButton btnSubmit, btnHome;
    private Adapter_Final_Exam_Summery adapter;
    private int totalQuestion = 0, totalCorrection = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_final_exam_summery, container, false);
        CancelOnBackPress();
        Bundle bundle = this.getArguments();
        finalExameId = bundle.getString("QuizeID");
        attemptRawId = bundle.getString("no_of_attempt");
        requiredAnswers = bundle.getString("RequiredAnswers");
        questionCount = bundle.getString("QuestionCount");
        Log.e("Atrg", attemptRawId);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnHome = view.findViewById(R.id.btnHome);
        recyclerView = view.findViewById(R.id.test_recycler);

        finalexamViewModel = new ViewModelProvider(this).get(View_model_finalexam.class);

        finalexamViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_final_exam_history>>() {
            @Override
            public void onChanged(List<Model_final_exam_history> model_final_exam_histories) {
                setupRecyclerView(model_final_exam_histories);
                if (model_final_exam_histories.size() == 0) {

                } else {
                    if (model_final_exam_histories.size() == 0) {
                        Log.e("FinalExamSummeryFrag", "Obser 2 : " + "Null");
                    } else {
                        for (Model_final_exam_history data : model_final_exam_histories) {
                            Log.e("FinalExamSummeryFrag", "Obser 1 : " + data.getQuiz_answer_id() + "//" + data.getQuiz_question() + "//" + data.getQuiz_question_id());
                        }
                    }
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitSummery();
            }
        });
        return view;
    }

    private void setupRecyclerView(List<Model_final_exam_history> model_quiz_histories) {
        if (model_quiz_histories.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.no_answers), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            recyclerView.setHasFixedSize(true);
            adapter = new Adapter_Final_Exam_Summery(getActivity());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setSummeryList(model_quiz_histories);
            recyclerView.setAdapter(adapter);
        }

    }

    private void SubmitSummery() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        finalexamViewModel = new ViewModelProvider(requireActivity()).get(View_model_finalexam.class);
        finalexamViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_final_exam_history>>() {
            @Override
            public void onChanged(List<Model_final_exam_history> model_quiz_histories) {

                for (Model_final_exam_history data : model_quiz_histories) {

                    String user_id = Login_Screen.getid;
                    String quiz_id = finalExameId;
                    String Quiz_q_Id = data.getQuiz_question_id();
                    String answer_id = data.getQuiz_answer_id();
                    String attempt = answer_id;

                    totalQuestion += data.getCorrect_status().length();

                    if (data.getCorrect_status().equals("1")) {
                        totalCorrection += data.getCorrect_status().length();
                    }

                    Call<Boolean> call = api_interface.PostFinalExamSummery("Bearer " + Login_Screen.gettoken, user_id, quiz_id, Quiz_q_Id, answer_id, attempt);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Log.e("FinalExamSummeryFrag", "onResponce : " + response.code());
                            if (response.code() == 200) {
                                Boolean checkdata = response.body();
                                Log.e("FinalExamSummeryFrag", "res : " + response.message() + checkdata);

                            } else {
                                Log.e("FinalExamSummeryFrag", "res : " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            PrograssHud.KProgressHudStop(getActivity());
                            Log.e("FinalExamSummeryFrag", "Fail: " + t.getMessage());
                            FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        }
                    });

                }
                Log.e("FinalExamSummeryFrag", "total question : " + totalQuestion);
                Log.e("FinalExamSummeryFrag", "total correction : " + totalCorrection);
                GetFinalExamAttempt(finalExameId, String.valueOf(totalCorrection));
            }
        });

    }


    private void GetFinalExamAttempt(String finalExamId, String totalCorrection) {
        Log.e("FinalExamSummeryFrag ff", totalCorrection +"//"+Login_Screen.getid+"//" + attemptRawId+"//"+finalExamId);
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamAttemptUpdate_Model>> call = api_interface.GetFinalExamAttempt("Bearer " + Login_Screen.gettoken, totalCorrection, Login_Screen.getid, finalExamId, attemptRawId);
        call.enqueue(new Callback<List<FinalExamAttemptUpdate_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamAttemptUpdate_Model>> call, Response<List<FinalExamAttemptUpdate_Model>> response) {
                List<FinalExamAttemptUpdate_Model> data = response.body();
                if (response.code() == 200) {
                    PrograssHud.KProgressHudStop(getActivity());
                    Log.e("FinalExamSummeryFrag 1", String.valueOf(response.code()));
                    Log.e("FinalExamSummeryFrag 2", data.get(0).getAttemptId());
                    MoveToScore();
                    btnSubmit.setEnabled(false);
                } else {
                    Log.e("FinalExamSummeryFrag", String.valueOf(response.code()));
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<FinalExamAttemptUpdate_Model>> call, Throwable t) {
                Log.e("FinalExamSummeryFrag", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });
    }

    private void MoveToScore() {
        Bundle bundle = new Bundle();
        bundle.putString("TotalQuestion", String.valueOf(totalQuestion));
        bundle.putString("TotalCorrection", String.valueOf(totalCorrection));
        bundle.putString("QuestionCount", String.valueOf(questionCount));
        bundle.putString("RequiredAnswers", requiredAnswers);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new FinalExamPassFragment();
        mFrag.setArguments(bundle);
        t.replace(R.id.frame, mFrag);
        t.addToBackStack(ModuleFragment.class.getName());
        t.commit();
    }

    private void CancelOnBackPress() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Log.e("QuizeFragment", "back");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}