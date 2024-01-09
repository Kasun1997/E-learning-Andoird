package com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizQuestion_Model;

import java.util.ArrayList;
import java.util.List;

public class Repo_quiz {
    private static MutableLiveData<List<Model_quiz_history>> allLiveData = new MediatorLiveData<>();
    private static Repo_quiz repo_quiz = null;
    private Context context;

    public Repo_quiz() {
    }

    public static Repo_quiz getInstance() {
        if (repo_quiz == null)
            repo_quiz = new Repo_quiz();

        return repo_quiz;
    }

    public static LiveData<List<Model_quiz_history>> getAllLiveData() {
        if (allLiveData.getValue() == null) {
            Log.e("Repo_Quiz", "getAllLiveData:null");
            allLiveData.setValue(new ArrayList<Model_quiz_history>());
        }

        List<Model_quiz_history> quiz_histories = new ArrayList<>(allLiveData.getValue());

        return allLiveData;
    }

    public void Insert(QuizQuestion_Model question, QuizAnswer_Model answer, int position) {
        if (allLiveData.getValue() == null) {
            allLiveData.setValue(new ArrayList<Model_quiz_history>());
        }
        List<Model_quiz_history> modelQuizHistories = new ArrayList<>(allLiveData.getValue());

        for (Model_quiz_history history : modelQuizHistories) {

            Log.e("Repo_Quiz", " ///// "+question.getId()+"**"+history.getQuiz_question_id());

            if (question.getId() == history.getQuiz_question_id()) {
                int index = modelQuizHistories.indexOf(history);
                modelQuizHistories.get(index).setQuiz_answer_id(answer.getAnswersId());
                modelQuizHistories.get(index).setPostion(position);
                modelQuizHistories.get(index).setCorrect_status(answer.getCorrectStatus());
                allLiveData.setValue(modelQuizHistories);
                Log.e("Repo_Quiz", " //* "+question.getId()+"//"+answer.getAnswersId());

                return;
            }
        }
        Model_quiz_history quizHistory=new Model_quiz_history(question.getId(),answer.getAnswersId(),question.getName(),answer.getCorrectStatus(),position);
        Log.e("Repo_Quiz", " // "+question.getId()+"//"+answer.getAnswersId());
        modelQuizHistories.add(quizHistory);
        allLiveData.setValue(modelQuizHistories);
    }

    public void ClearHistory(){
        List<Model_quiz_history> history=new ArrayList<>(allLiveData.getValue());

        history.clear();

        allLiveData.setValue(history);
    }
}
