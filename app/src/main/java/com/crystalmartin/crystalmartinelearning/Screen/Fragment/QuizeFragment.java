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
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Answer;
import com.crystalmartin.crystalmartinelearning.Adapter.Adapter_Answer;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Api_Interface;
import com.crystalmartin.crystalmartinelearning.ApiSegment.Retrofit_Client;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront.Model_quiz_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront.View_model_quiz_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Model_quiz_history;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.View_model_quiz_history;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizQuestion_Model;

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


public class QuizeFragment extends Fragment {

    private View view;
    private String quizeId, attemptNo, moduleId, answerId, messege, trainigId, courseId, requiredAnswers, permission, moduleName, trainingSheduleId;
    private JustifiedTextView txtvwQuestion;
    private TextView txvwQuizlevel, txvwTitle;

    private List<QuizQuestion_Model> quizeQuestions_m_gets;
    private List<QuizAnswer_Model> quizAnswer_m_gets;

    private AppCompatButton btnPrevious, btnNext;
    private int x, adapterPosition, questionCount;

    private View_model_quiz_history quizViewModel;
    private View_model_quiz_history_back_front back_front_ViewModel;

    private RecyclerView recyclerView;
    private Adapter_Answer adapter_answer;

    public static String savedAnswerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quize, container, false);
        CancelOnBackPress();
        Initializing();


        Bundle bundle = this.getArguments();
        quizeId = bundle.getString("QuizeID");
        attemptNo = bundle.getString("no_of_attempt");
        moduleId = bundle.getString("moduleID");
        trainigId = bundle.getString("TrainigId");
        courseId = bundle.getString("CourseID");
        requiredAnswers = bundle.getString("RequiredAnswers");
        moduleName = bundle.getString("ModuleName");
        trainingSheduleId = bundle.getString("TrainigSheduleId");


        txvwTitle.setText(moduleName + "\n" + "- Quiz");

        Log.e("CHECK_ALL_ID", trainigId + "//" + courseId + "//" + moduleId);
        Log.e("CHECK_ALL_ID", "testId " + quizeId + "//" + attemptNo + "//" + trainigId + "//" + courseId + "///" + requiredAnswers);

        quizeQuestions_m_gets = new ArrayList<>();
        GetQuizeQuestion();
        quizAnswer_m_gets = new ArrayList<>();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x++;
                Log.e("QuizeFragment", "CHeck" + String.valueOf(x));
                btnPrevious.setEnabled(true);
                QuizQuestionView(x);

                UpdateAnswers(quizeQuestions_m_gets.get(x - 1).getId(), answerId, adapterPosition);
                getpreviousAnswerID(x);
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x--;
                Log.e("QuizeFragment", "CHeck" + String.valueOf(x));
                if (x < 0) {
                    Log.e("QuizeFragment", "CHeck 1 : " + String.valueOf(x));
                    btnPrevious.setEnabled(false);
                    x++;
                } else {
                    Log.e("QuizeFragment", "CHeck 2 : " + String.valueOf(x));
                    btnNext.setEnabled(true);
                    answerId = null;
                    QuizQuestionView(x);
                    getpreviousAnswerID(x);//now
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
        txvwTitle = view.findViewById(R.id.txvwTitle);
        quizViewModel = new ViewModelProvider(this).get(View_model_quiz_history.class);
        back_front_ViewModel = new ViewModelProvider(this).get(View_model_quiz_history_back_front.class);
    }

    private void GetQuizeQuestion() {
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));
        // othereResources_m_gets.clear();
        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<QuizQuestion_Model>> call = api_interface.GetQuizeQuestion("Bearer " + Login_Screen.gettoken, quizeId, SplashScreen.languageID);
        call.enqueue(new Callback<List<QuizQuestion_Model>>() {
            @Override
            public void onResponse(Call<List<QuizQuestion_Model>> call, Response<List<QuizQuestion_Model>> response) {
                Log.e("QuizeFragment", "onResponce : " + response.code());
                if (response.code() == 200) {

                    Log.e("QuizeFragment", "res : " + response.message());

                    List<QuizQuestion_Model> data = response.body();
                    Collections.shuffle(data); //shuffle
                    for (QuizQuestion_Model d : data) {
                        Log.e("QuizeFragment", "Name : " + d.getName() + "//" + d.getId());

                        quizeQuestions_m_gets.add(d);
                        PrograssHud.KProgressHudStop(getActivity());

                    }
                    QuizQuestionView(0);
                } else {
                    Log.e("QuizeFragment", "res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<QuizQuestion_Model>> call, Throwable t) {
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });
    }

    private void QuizQuestionView(int i) {

        questionCount = quizeQuestions_m_gets.size();

        if (quizeQuestions_m_gets.size() == 0) {

            Log.e("QuizeFragment", "yy" + " ArrayList equeal 0 : " + quizeQuestions_m_gets.size());

        } else {
            if (questionCount > i) {

                txtvwQuestion.setText(quizeQuestions_m_gets.get(i).getName());
                GetQuizAnswers(quizeQuestions_m_gets.get(i).getId(), quizeQuestions_m_gets.get(i).getName());//now
                txvwQuizlevel.setText("Question " + String.valueOf(i + 1) + " of " + String.valueOf(questionCount));

                if (questionCount - 1 == i) {

                    btnNext.setText(R.string.sumbit);

                } else {

                    btnNext.setText(R.string.next);
                }

            } else {
                Log.e("QuizeFragment", "yy" + " finish " + i);
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

    private void GetQuizAnswers(String id, String question) {
        quizAnswer_m_gets.clear();
        PrograssHud.KProgressHudStart(getActivity(), getString(R.string.loading_training), getString(R.string.wait_for_while));

        Api_Interface api_interface = Retrofit_Client.getRetrofitInstance().create(Api_Interface.class);
        Call<List<QuizAnswer_Model>> call = api_interface.GetQuizeAnswers("Bearer " + Login_Screen.gettoken, id, SplashScreen.languageID);
        call.enqueue(new Callback<List<QuizAnswer_Model>>() {
            @Override
            public void onResponse(Call<List<QuizAnswer_Model>> call, Response<List<QuizAnswer_Model>> response) {
                Log.e("QuizeFragmentA", "Ans onResponce : " + response.code());

                if (response.code() == 200) {

                    Log.e("QuizeFragmentA", "Ans res : " + response.message());

                    List<QuizAnswer_Model> data = response.body();
                    for (QuizAnswer_Model d : data) {
                        Log.e("QuizeFragmentA", "Ans Name : " + d.getName() + "//" + d.getId());
                        Log.e("QuizeFragmentA", "Ans Size : " + data.size());

                        quizAnswer_m_gets.add(d);
                    }
                    QuizQuestion_Model questionGet = new QuizQuestion_Model(id, question); //pass question to LiveData //now
                    SetUpRecycler(quizAnswer_m_gets, questionGet);

                } else {
                    Log.e("QuizeFragmentA", "Ans res : " + response.message());
                    FancyToast.makeText(getContext(), getString(R.string.fail_to), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    PrograssHud.KProgressHudStop(getActivity());
                }
            }

            @Override
            public void onFailure(Call<List<QuizAnswer_Model>> call, Throwable t) {
                PrograssHud.KProgressHudStop(getActivity());
                FancyToast.makeText(getContext(), getString(R.string.session), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
            }
        });

    }

    private void SetUpRecycler(List<QuizAnswer_Model> quizAnswer_m_gets, QuizQuestion_Model questions) {

        if (quizAnswer_m_gets.size() == 0) {
            FancyToast.makeText(getContext(), getString(R.string.not_available), FancyToast.LENGTH_SHORT, FancyToast.INFO, false).show();
            PrograssHud.KProgressHudStop(getActivity());
        } else {
            Log.e("QuizeFragment", "CheckRecyclerSize" + " " + quizAnswer_m_gets.size());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter_answer = new Adapter_Answer(getContext(), quizAnswer_m_gets);
            recyclerView.setAdapter(adapter_answer);
            adapter_answer.setAnswerShuffle(quizAnswer_m_gets); //shuffle
            recyclerView.scheduleLayoutAnimation();
            PrograssHud.KProgressHudStop(getActivity());

            adapter_answer.setOnItemClickListener(new Adapter_Answer.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    QuizAnswer_Model model = quizAnswer_m_gets.get(position);//pass answer to LiveData
                    Log.e("QuizeFragment", "onclick ans Id : " + String.valueOf(position) + " /**/ " + model.getAnswersId() + "///" + model.getCorrectStatus());

                    QuizQuestion_Model quizModel = new QuizQuestion_Model(questions.getId(), questions.getName()); //now
                    QuizAnswer_Model answer = new QuizAnswer_Model(model.getAnswersId(), model.getCorrectStatus());
                    quizViewModel.AddQuestion(quizModel, answer, position);
                    adapterPosition = position;
                    answerId = model.getAnswersId();

                    Log.e("DDDDDDDDD", answerId + "//" + questions.getId());
                }
            });

        }

    }

    private void getpreviousAnswerID(int i) { //btnPrevious

        back_front_ViewModel = new ViewModelProvider(requireActivity()).get(View_model_quiz_history_back_front.class);
        back_front_ViewModel.GetAnswerPosition().observe(getViewLifecycleOwner(), new Observer<List<Model_quiz_history_back_front>>() {
            @Override
            public void onChanged(List<Model_quiz_history_back_front> model_quiz_history_back_fronts) {
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
        back_front_ViewModel.AddAnswerPosition(questionId, answersId, posotion);
        answerId = null;
        adapterPosition = -1;
        Log.e("QuizeFragment", "observe " + " save ans id 2 : " + savedAnswerId);
    }

    private void GetNoOfAnsweredQuestions() {

        quizViewModel = new ViewModelProvider(requireActivity()).get(View_model_quiz_history.class);
        quizViewModel.GetAnsQuestions().observe(getViewLifecycleOwner(), new Observer<List<Model_quiz_history>>() {
            @Override
            public void onChanged(List<Model_quiz_history> answeredModels) {
                Log.e("CheckSizeewewe", "" + answeredModels.size());
                if (answeredModels.size() == 0) {
                    messege = getString(R.string.complete_only);
                    permission = "0";
                } else {
                    if (questionCount == answeredModels.size()) {
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
        bundle.putString("QuizeID", quizeId);
        bundle.putString("no_of_attempt", attemptNo);
        bundle.putString("moduleID", moduleId);
        bundle.putString("QuestionCount", String.valueOf(questionCount));
        bundle.putString("RequiredAnswers", requiredAnswers);
        bundle.putString("CourseID", courseId);
        bundle.putString("TrainigId", trainigId);
        bundle.putString("TrainigSheduleId", trainingSheduleId);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction t = fragmentManager.beginTransaction();
        Fragment mFrag = new SummeryFragment();
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

