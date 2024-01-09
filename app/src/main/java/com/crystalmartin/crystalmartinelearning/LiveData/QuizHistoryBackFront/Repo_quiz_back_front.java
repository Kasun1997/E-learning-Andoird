package com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Model_quiz_history;

import java.util.ArrayList;
import java.util.List;

public class Repo_quiz_back_front {
    private static MutableLiveData<List<Model_quiz_history_back_front>> liveData = new MediatorLiveData<>();
    private static Repo_quiz_back_front repo_quiz_back_front = null;
    private Context context;

    public Repo_quiz_back_front() {
    }

    public static Repo_quiz_back_front getInstance() {
        if (repo_quiz_back_front == null)
            repo_quiz_back_front = new Repo_quiz_back_front();

        return repo_quiz_back_front;
    }

    public static LiveData<List<Model_quiz_history_back_front>> getAllLiveData_B_F() {
        if (liveData.getValue() == null) {
            Log.e("Repo_quiz_back_front", "getAllLiveData:null");
            liveData.setValue(new ArrayList<Model_quiz_history_back_front>());
        }

        List<Model_quiz_history_back_front> quiz_history_back_fronts = new ArrayList<>(liveData.getValue());

        return liveData;
    }

    public void Insert(String questionID, String ansID, int position) {
        if (liveData.getValue()==null){
            liveData.setValue(new ArrayList<Model_quiz_history_back_front>());
        }
        List<Model_quiz_history_back_front> model_quiz_history_back_fronts=new ArrayList<>(liveData.getValue());

        for (Model_quiz_history_back_front modelHistoryBackFront : model_quiz_history_back_fronts){

            if (questionID==modelHistoryBackFront.getQuiz_question_id()){
                int index=model_quiz_history_back_fronts.indexOf(modelHistoryBackFront);
                model_quiz_history_back_fronts.get(index).setQuiz_answer_id(ansID);
                model_quiz_history_back_fronts.get(index).setPostion(position);
                liveData.setValue(model_quiz_history_back_fronts);

                return;
            }
        }

        Model_quiz_history_back_front model=new Model_quiz_history_back_front(questionID,ansID,position);
        model_quiz_history_back_fronts.add(model);
        liveData.setValue(model_quiz_history_back_fronts);
    }

    public void ClearHistory(){
        List<Model_quiz_history_back_front> back_fronts=new ArrayList<>(liveData.getValue());

        back_fronts.clear();

        liveData.setValue(back_fronts);
    }
}
