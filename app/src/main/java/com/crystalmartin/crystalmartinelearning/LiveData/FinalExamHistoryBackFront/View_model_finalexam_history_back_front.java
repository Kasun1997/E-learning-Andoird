package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryBackFront;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class View_model_finalexam_history_back_front extends ViewModel {

    Repo_finalexam_back_front repo_finalexam_back_front=Repo_finalexam_back_front.getInstance();

    public LiveData<List<Model_finalexam_history_back_front>>GetAnswerPosition(){
        return repo_finalexam_back_front.getAllLiveDataFinal_B_F();
    }

    public void AddAnswerPosition(String questionID, String ansID, int position){
        repo_finalexam_back_front.Insert(questionID,ansID,position);
    }

    public void ClearHistory(){
        repo_finalexam_back_front.ClearHistory();
    }

}
