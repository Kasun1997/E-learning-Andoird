package com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizQuestion_Model;

import java.util.List;

public class View_model_quiz_history extends ViewModel {

    Repo_quiz repo_quiz=Repo_quiz.getInstance();

    public LiveData<List<Model_quiz_history>> GetAnsQuestions(){
        return repo_quiz.getAllLiveData();
    }

    public void AddQuestion(QuizQuestion_Model question, QuizAnswer_Model answer, int posotion){
        repo_quiz.Insert(question,answer,posotion);
    }

    public void ClearHistory(){

        repo_quiz.ClearHistory();
    }

}
