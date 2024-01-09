package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAnswers_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuestion_Model;

import java.util.List;

public class View_model_finalexam extends ViewModel {

    Repo_finalexam repo_finalexam=Repo_finalexam.getInstance();

    public LiveData<List<Model_final_exam_history>>GetAnsQuestions(){
        return repo_finalexam.getAllLiveDataSet();
    }

    public void AddQuestion(FinalExamQuestion_Model question, FinalExamAnswers_Model answer,int position){
        repo_finalexam.Insert(question,answer,position);
    }

    public void ClearHistory(){
        repo_finalexam.ClearHistory();
    }
}
