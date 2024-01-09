package com.crystalmartin.crystalmartinelearning.Screen.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codesgood.views.JustifiedTextView;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Final_Answer;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront.Model_finalexam_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront.View_model_finalexam_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.Model_final_exam_history;
import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.View_model_finalexam;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAnswers_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuestion_Model;
import com.crystalmartin.crystalmartinelearning.R;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.Login_Screen;
import com.crystalmartin.crystalmartinelearning.Screen.Activity.SplashScreen;
import com.crystalmartin.crystalmartinelearning.Util.PrograssHud;
import com.crystalmartin.crystalmartinelearning.Util.SweetAlert;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FinalExamFragment extends Fragment {

    private View view;
    private JustifiedTextView txtvwQuestion;
    private TextView txvwQuizlevel;
    private AppCompatButton btnPrevious, btnNext;
    private RecyclerView recyclerView;
    private String finalExameId, attemptRawId, requiredAnswers, answerId, permission, messege;

    private List<FinalExamQuestion_Model> finalExamQuestion_models;
    private List<FinalExamAnswers_Model> finalExamAnswers_models;

    private View_model_finalexam finalexamViewModel;
    private View_model_finalexam_history_back_front history_back_frontViewModel;

    private Adapter_Final_Answer adapter_answer;
    private int x, adapterPosition, questionCount;

    public static String savedAnswerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_final_exam, container, false);
        CancelOnBackPress();
        Initializing();

        Bundle bundle = this.getArguments();
        finalExameId = bundle.getString("FinalExamID");
        attemptRawId = bundle.getString("AttemptRawId");
        requiredAnswers = bundle.getString("RequiredAnswers");

        Log.e("FinalExamFragment", finalExameId);

        finalExamQuestion_models = new ArrayList<>();
        GetFinalQuizQuestion(finalExameId);
        finalExamAnswers_models = new ArrayList<>();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                btnPrevious.setEnabled(true);
                FinalQuizQuestionView(x);

                UpdateAnswers(finalExamQuestion_models.get(x - 1).getExamQuizz_Id(), answerId, adapterPosition);
                getpreviousAnswerID(x);

            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x--;
                if (x < 0) {
                    btnPrevious.setEnabled(false);
                    Log.e("FFFF", "HHH");
                    x++;
                } else {
                    Log.e("FFFF", "YYY");
                    btnNext.setEnabled(true);
                    answerId = null;
                    FinalQuizQuestionView(x);
                    getpreviousAnswerID(x);
                }
            }
        });

        return view;
    }

    private void Initializing() {
        txtvwQuestion = view.findViewById(R.id.JtxtvwQuestion);
        txvwQuizlevel = view.findViewById(R.id.txvwQuizlevel);
        btnPrevious = view.findViewById(R.id.prev);
        btnNext = view.findViewById(R.id.next);
        recyclerView = view.findViewById(R.id.recycleAnswers);
        finalexamViewModel = new ViewModelProvider(this).get(View_model_finalexam.class);
        history_back_frontViewModel = new ViewModelProvider(this).get(View_model_finalexam_history_back_front.class);
    }

    private void GetFinalQuizQuestion(String finalExamId) {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamQuestion_Model>> call = api_interface.GetFinalExamQuestion("Bearer " + Login_Screen.gettoken, finalExamId, SplashScreen.languageID);
        call.enqueue(new Callback<List<FinalExamQuestion_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamQuestion_Model>> call, Response<List<FinalExamQuestion_Model>> response) {

                if (response.code() == 200) {

                    List<FinalExamQuestion_Model> data = response.body();
                    Collections.shuffle(data);//shuffle
                    for (FinalExamQuestion_Model d : data) {
                        Log.e("FinalExamFragment", "Name : " + d.getExamQuizz_Id() + "//" + d.getExamQuizzName());
                        finalExamQuestion_models.add(d);
                        PrograssHud.KProgressHudStop(getActivity());
                    }
                    FinalQuizQuestionView(0);


                } else {
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }

            }

            @Override
            public void onFailure(Call<List<FinalExamQuestion_Model>> call, Throwable t) {
                Log.e("FinalExamFragment", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });

    }

    private void FinalQuizQuestionView(int i) {
        questionCount = finalExamQuestion_models.size();
        Log.e("FinalExamFragment", "size : " + String.valueOf(questionCount));

        if (finalExamQuestion_models.size() == 0) {
            Log.e("QuizeFragment", "final" + " ArrayList equeal 0 : " + finalExamQuestion_models.size());
        } else {
            if (questionCount > i) {

                txtvwQuestion.setText(finalExamQuestion_models.get(i).getExamQuizzName());
                GetFinalQuizAnswers(finalExamQuestion_models.get(i).getExamQuizz_Id(), finalExamQuestion_models.get(i).getExamQuizzName());
                txvwQuizlevel.setText("Question " + String.valueOf(i + 1) + " of " + String.valueOf(questionCount));

                if (questionCount - 1 == i) {
                    Log.e("QuizeDDFragment", "submit");
                    btnNext.setText(R.string.sumbit);

                } else {
                    Log.e("QuizeDDFragment", "next");
                    btnNext.setText(R.string.next);
                }
            } else {
                btnNext.setEnabled(false);
                GetNoOfAnsweredQuestions();
                if (permission == "1") {
                    SweetAlert.SweetAlertWarning(getActivity(), getString(R.string.sumbit), messege, getString(R.string.sumbit), getString(R.string.cancel));
                    SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            MoveToSummery();//now
                            sweetAlertDialog.dismiss();
                        }
                    });
                    SweetAlert.pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismiss();
                        }
                    });
                } else {
                    SweetAlert.SweetAlertCommand(getActivity(), getString(R.string.notice), messege, getString(R.string.ok), R.drawable.icon_quize_l);
                    SweetAlert.pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            sweetAlertDialog.dismiss();
                        }
                    });
                }
                x--;
            }
        }
    }

    private void GetFinalQuizAnswers(String id, String question) {
        finalExamAnswers_models.clear();
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));

        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<FinalExamAnswers_Model>> call = api_interface.GetFinalExamAnswers("Bearer " + Login_Screen.gettoken, id, SplashScreen.languageID);
        call.enqueue(new Callback<List<FinalExamAnswers_Model>>() {
            @Override
            public void onResponse(Call<List<FinalExamAnswers_Model>> call, Response<List<FinalExamAnswers_Model>> response) {
                Log.e("QuizeFragmentA", "Ans onResponce : " + response.code());

                if (response.code() == 200) {

                    Log.e("QuizeFragmentA", "Ans res : " + response.message());

                    List<FinalExamAnswers_Model> data = response.body();
                    for (FinalExamAnswers_Model d : data) {
                        Log.e("QuizeFragmentA", "Ans Name : " + d.getExamAnswersName() + "//" + d.getExamAnswers_Id());
                        Log.e("QuizeFragmentA", "Ans Size : " + data.size());

                        finalExamAnswers_models.add(d);
                    }
                    FinalExamQuestion_Model questionGet = new FinalExamQuestion_Model(id, question);
                    SetUpRecycler(finalExamAnswers_models, questionGet);

                } else {
                    Log.e("QuizeFragmentA", "Ans res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<FinalExamAnswers_Model>> call, Throwable t) {
                Log.e("FinalExamFragment", t.getMessage());
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });

    }

    private void SetUpRecycler(List<FinalExamAnswers_Model> quizAnswer_m_gets, FinalExamQuestion_Model questions) {

        if (quizAnswer_m_gets.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.not_available), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            Log.e("QuizeFragment", "CheckRecyclerSize" + " " + quizAnswer_m_gets.size());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_answer = new Adapter_Final_Answer(getContext(), quizAnswer_m_gets);
            recyclerView.setAdapter(adapter_answer);
            adapter_answer.setAnswerShuffle(quizAnswer_m_gets);
            recyclerView.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());


            adapter_answer.setOnItemClickListener(new Adapter_Final_Answer.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    FinalExamAnswers_Model model = quizAnswer_m_gets.get(position);//pass answer to LiveData
                    Log.e("FinalExamFragment", "onclick ans Id : " + String.valueOf(position) + " /**/ " + model.getExamAnswers_Id() + "///" + model.getCorrectStatus() + "///" + model.getExamAnswersName());

                    FinalExamQuestion_Model questionModel = new FinalExamQuestion_Model(questions.getExamQuizz_Id(), questions.getExamQuizzName());
                    FinalExamAnswers_Model answer = new FinalExamAnswers_Model(model.getExamAnswers_Id(), model.getCorrectStatus());
                    finalexamViewModel.AddQuestion(questionModel, answer, position);
                    adapterPosition = position;
                    answerId = model.getExamAnswers_Id();
                    Log.e("FinalExamFragment", "Obser 0 : " + model.getExamAnswers_Id() + "//" + model.getCorrectStatus());
                }
            });

        }

    }

    private void getpreviousAnswerID(int i) { //btnPrevious
        Log.e("QuizeFragment", "test " + i);
        history_back_frontViewModel = new ViewModelProvider(requireActivity()).get(View_model_finalexam_history_back_front.class);
        history_back_frontViewModel.GetAnswerPosition().observe(getViewLifecycleOwner(), new Observer<List<Model_finalexam_history_back_front>>() {
            @Override
            public void onChanged(List<Model_finalexam_history_back_front> model_quiz_history_back_fronts) {
                if (model_quiz_history_back_fronts.size() == 0) {
                    Log.e("QuizeFragment", "observe " + " Empty");
                } else {
                    Log.e("QuizeFragment", "observe size : " + model_quiz_history_back_fronts.size() + " x is: " + i);
                    if (model_quiz_history_back_fronts.size() > i) {
                        savedAnswerId = model_quiz_history_back_fronts.get(i).getQuiz_answer_id();
                        Log.e("QuizeFragment", "observe " + " save ans id 1 : " + savedAnswerId);
                    } else {
                        Log.e("QuizeFragment", "observe " + " save ans id 11 : ");
                    }

                }
            }
        });
    }

    private void UpdateAnswers(String questionId, String answersId, int posotion) { //btnNext

        if (savedAnswerId == null) {

            if (answersId == null) {
                Procedure(questionId, savedAnswerId, posotion);
                Log.e("QuizeFragment", "observe " + " inside if : 01 ");

            } else {
                Procedure(questionId, answersId, posotion);
                Log.e("QuizeFragment", "observe " + " inside if : 02 ");
            }

        } else {

            if (answersId == null) {
                Procedure(questionId, savedAnswerId, posotion);
                Log.e("QuizeFragment", "observe " + " inside if : 03");
                savedAnswerId = null;
            } else {
                Procedure(questionId, answersId, posotion);
                Log.e("QuizeFragment", "observe " + " inside if : 04 ");

            }

        }

    }

    private void Procedure(String questionId, String answersId, int posotion) {
        history_back_frontViewModel.AddAnswerPosition(questionId, answersId, posotion);
        answerId = null;
        adapterPosition = -1;
        Log.e("QuizeFragment", "observe " + " save ans id 2 : " + savedAnswerId);
    }


    private void GetNoOfAnsweredQuestions() {
        finalexamViewModel = new ViewModelProvider(requireActivity()).get(View_model_finalexam.class);
        finalexamViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_final_exam_history>>() {
            @Override
            public void onChanged(List<Model_final_exam_history> model_final_exam_histories) {
                Log.e("FinalExamFragment", "Obser 1 : " + model_final_exam_histories.size());
                if (model_final_exam_histories.size() == 0) {
                    messege = getString(R.string.complete_only);
                    permission = "0";
                } else {
                    if (questionCount == model_final_exam_histories.size()) {
                        messege = getString(R.string.once_you_submit_you);
                        permission = "1";
                    } else {
                        messege = getString(R.string.complete_only);
                        permission = "0";
                    }

                }
            }
        });
    }

    private void MoveToSummery() {
        Bundle bundle = new Bundle();
        bundle.putString("QuizeID", finalExameId);
        bundle.putString("no_of_attempt", attemptRawId);
//        bundle.putString("moduleID", moduleId);
        bundle.putString("QuestionCount", String.valueOf(questionCount));
        bundle.putString("RequiredAnswers", requiredAnswers);
//        bundle.putString("CourseID", courseId);
//        bundle.putString("TrainigId", trainigId);
//        bundle.putString("TrainigSheduleId", trainingSheduleId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new FinalExamSummeryFragment();
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