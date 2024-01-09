package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView.Model_final_exam_history;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront.Model_quiz_history_back_front;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront.Repo_quiz_back_front;

import java.util.ArrayList;
import java.util.List;

public class Repo_finalexam_back_front {

    private static MutableLiveData<List<Model_finalexam_history_back_front>> liveData = new MediatorLiveData<>();
    private static Repo_finalexam_back_front repo_finalexam_back_front = null;
    private Context context;

    public Repo_finalexam_back_front() {
    }

    public static Repo_finalexam_back_front getInstance(){
        if (repo_finalexam_back_front==null)
            repo_finalexam_back_front=new Repo_finalexam_back_front();

        return repo_finalexam_back_front;
    }

    public static LiveData<List<Model_finalexam_history_back_front>> getAllLiveDataFinal_B_F() {
        if (liveData.getValue() == null) {
            Log.e("Repo_quiz_back_front", "getAllLiveData:null");
            liveData.setValue(new ArrayList<Model_finalexam_history_back_front>());
        }

        List<Model_finalexam_history_back_front> quiz_history_back_fronts = new ArrayList<>(liveData.getValue());

        return liveData;
    }

    public void Insert(String questionID, String ansID, int position) {
        if (liveData.getValue()==null){
            liveData.setValue(new ArrayList<Model_finalexam_history_back_front>());
        }
        List<Model_finalexam_history_back_front> model_finalexam_history_back_fronts=new ArrayList<>(liveData.getValue());

        for (Model_finalexam_history_back_front modelHistoryBackFront : model_finalexam_history_back_fronts){

            if (questionID==modelHistoryBackFront.getQuiz_question_id()){
                int index=model_finalexam_history_back_fronts.indexOf(modelHistoryBackFront);
                model_finalexam_history_back_fronts.get(index).setQuiz_answer_id(ansID);
                model_finalexam_history_back_fronts.get(index).setPostion(position);
                liveData.setValue(model_finalexam_history_back_fronts);

                return;
            }
        }

        Model_finalexam_history_back_front model=new Model_finalexam_history_back_front(questionID,ansID,position);
        model_finalexam_history_back_fronts.add(model);
        liveData.setValue(model_finalexam_history_back_fronts);
    }

    public void ClearHistory(){
        List<Model_finalexam_history_back_front> back_fronts=new ArrayList<>(liveData.getValue());

        back_fronts.clear();

        liveData.setValue(back_fronts);
    }

}
