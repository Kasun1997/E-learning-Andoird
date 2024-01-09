package com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryBackFront;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class View_model_quiz_history_back_front extends ViewModel {

    Repo_quiz_back_front repo_quiz_back_front=Repo_quiz_back_front.getInstance();

    public LiveData<List<Model_quiz_history_back_front>> GetAnswerPosition(){
        return repo_quiz_back_front.getAllLiveData_B_F();
    }

    public void AddAnswerPosition(String questionID, String ansID, int position){
        repo_quiz_back_front.Insert(questionID,ansID,position);
    }

    public void ClearHistory(){
        repo_quiz_back_front.ClearHistory();
    }
}
