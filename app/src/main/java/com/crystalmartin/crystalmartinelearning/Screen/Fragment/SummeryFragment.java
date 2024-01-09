package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Summery;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Model_quiz_history;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.View_model_quiz_history;
import com.crystalmartin.crystalmartinelearning.Model_Real.UpdateAttempt_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SummeryFragment extends Fragment {

    private View view;
    private RecyclerView test_recycler;
    private Adapter_Summery adapter;
    private View_model_quiz_history quizViewModel;
    private AppCompatButton btnSubmit, btnHome;
    private ImageView imgNoanswer;
    private String quizeId, attemptNo, moduleId, questionCount, requiredAnswers, trainigId, courseId,trainingSheduleId;
    private int totalQuestion = 0, totalCorrection = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_summery, container, false);
        CancelOnBackPress();

        Bundle bundle = this.getArguments();
        quizeId = bundle.getString("QuizeID");
        attemptNo = bundle.getString("no_of_attempt");
        moduleId = bundle.getString("moduleID");
        questionCount = bundle.getString("QuestionCount");
        requiredAnswers = bundle.getString("RequiredAnswers");
        trainigId = bundle.getString("TrainigId");
        courseId = bundle.getString("CourseID");
        trainingSheduleId = bundle.getString("TrainigSheduleId");


        Log.e("CHECK_ALL_ID", trainigId + "//" + courseId + "//" + moduleId);
        Log.e("CHECK_ALL_ID", "test : " + quizeId + "//" + questionCount + "//" + attemptNo + "//" + requiredAnswers);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnHome = view.findViewById(R.id.btnHome);
        imgNoanswer = view.findViewById(R.id.imgNoanswer);
        test_recycler = view.findViewById(R.id.test_recycler);
        quizViewModel = new ViewModelProvider(this).get(View_model_quiz_history.class);


        quizViewModel = new ViewModelProvider(requireActivity()).get(View_model_quiz_history.class);
        quizViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_quiz_history>>() {
            @Override
            public void onChanged(List<Model_quiz_history> model_quiz_histories) {
                Log.e("SummeryFragment", "res test : " + model_quiz_histories.get(0).getQuiz_answer_id());
                setupRecyclerView(model_quiz_histories);
                if (model_quiz_histories.size() == 0) {
                    imgNoanswer.setVisibility(View.VISIBLE);
                    btnHome.setVisibility(View.VISIBLE);
                    btnSubmit.setVisibility(View.INVISIBLE);
                } else {
                    imgNoanswer.setVisibility(View.INVISIBLE);
                    btnHome.setVisibility(View.INVISIBLE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SubmitSummery();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoveToHome();
            }
        });
        return view;
    }

    private void setupRecyclerView(List<Model_quiz_history> model_quiz_histories) {
        if (model_quiz_histories.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.no_answers), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
        } else {
            test_recycler.setHasFixedSize(true);
            adapter = new Adapter_Summery(getActivity());
            test_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter.setSummeryList(model_quiz_histories);
            test_recycler.setAdapter(adapter);
        }

    }

    private void SubmitSummery() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        quizViewModel = new ViewModelProvider(requireActivity()).get(View_model_quiz_history.class);
        quizViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_quiz_history>>() {
            @Override
            public void onChanged(List<Model_quiz_history> model_quiz_histories) {

                for (Model_quiz_history data : model_quiz_histories) {

                    String user_id = Login_Screen.getid;
                    String quiz_id = quizeId;
                    String Quiz_q_Id = data.getQuiz_question_id();
                    String answer_id = data.getQuiz_answer_id();
                    String attempt = attemptNo;

                    totalQuestion += data.getCorrect_status().length();

                    if (data.getCorrect_status().equals("1")) {
                        totalCorrection += data.getCorrect_status().length();
                    }

                    Call<Boolean> call = api_interface.PostSummery("Bearer " + Login_Screen.gettoken, user_id, quiz_id, Quiz_q_Id, answer_id, attempt);
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Log.e("SummeryFragment", "onResponce : " + response.code());
                            if (response.code() == 200) {
                                Boolean checkdata = response.body();
                                Log.e("SummeryFragment", "res : " + response.message() + checkdata);

                            } else {
                                Log.e("SummeryFragment", "res : " + response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {
                            PrograssHud.KProgressHudStop(getActivity());
                            Log.e("SummeryFragment", "Fail: " + t.getMessage());
                            FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                        }
                    });

                }
                Log.e("SummeryFragment", "total question : " + totalQuestion);
                Log.e("SummeryFragment", "total correction : " + totalCorrection);
                PostQuizeAttempt(quizeId, String.valueOf(totalCorrection));
            }
        });

    }


    private void PostQuizeAttempt(String quizId, String mark) {
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Log.e("CECK", mark + "//" + Login_Screen.getid + "//" + quizId + "//" + attemptNo + "//" + trainigId+"//"+courseId+"//"+moduleId);
        Call<List<UpdateAttempt_Model>> call = api_interface.PostQuizAttempt("Bearer " + Login_Screen.gettoken, mark, Login_Screen.getid, Integer.parseInt(quizId), attemptNo, trainigId, courseId, moduleId,trainingSheduleId);
        call.enqueue(new Callback<List<UpdateAttempt_Model>>() {
            @Override
            public void onResponse(Call<List<UpdateAttempt_Model>> call, Response<List<UpdateAttempt_Model>> response) {
                Log.e("SummeryFragment", "Res Attempt : " + response.code());
                if (response.code() == 200) {
                    PrograssHud.KProgressHudStop(getActivity());
                    MoveToScore();
                    btnSubmit.setEnabled(false);
                } else {
                    Log.e("StudyMaterialFragment", "not success");
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<UpdateAttempt_Model>> call, Throwable t) {
                Log.e("SummeryFragment", "Attempt error : " + t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
            }
        });

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

    private void MoveToScore() {
        Bundle bundle = new Bundle();
        bundle.putString("TotalQuestion", String.valueOf(totalQuestion));
        bundle.putString("TotalCorrection", String.valueOf(totalCorrection));
        bundle.putString("moduleID", moduleId);
        bundle.putString("QuestionCount", String.valueOf(questionCount));
        bundle.putString("RequiredAnswers", requiredAnswers);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new QuizPassFragment();
        mFrag.setArguments(bundle);
        t.replace(R.id.frame, mFrag);
        t.addToBackStack(ModuleFragment.class.getName());
        t.commit();
    }

    private void MoveToHome() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new HomeFragment();
        t.replace(R.id.frame, mFrag);
        t.commit();
    }

}
