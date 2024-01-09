package com.crystalmartin.crystalmartinelearning.LiveData.FinalExamHistoryView;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Model_quiz_history;
import com.crystalmartin.crystalmartinelearning.LiveData.QuizHistoryView.Repo_quiz;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamAnswers_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.FinalExamQuestion_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizAnswer_Model;
import com.crystalmartin.crystalmartinelearning.Model_Real.QuizQuestion_Model;

import java.util.ArrayList;
import java.util.List;

public class Repo_finalexam {

    private static MutableLiveData<List<Model_final_exam_history>> allLiveData = new MediatorLiveData<>();
    private static Repo_finalexam repoFinalexam = null;
    private Context context;

    public Repo_finalexam() {
    }

    public static Repo_finalexam getInstance() {
        if (repoFinalexam==null)
            repoFinalexam=new Repo_finalexam();

        return repoFinalexam;
    }

    public static LiveData<List<Model_final_exam_history>> getAllLiveDataSet() {
        if (allLiveData.getValue() == null) {
            Log.e("Repo_Quiz", "getAllLiveData:null");
            allLiveData.setValue(new ArrayList<Model_final_exam_history>());
        }

        List<Model_final_exam_history> quiz_histories = new ArrayList<>(allLiveData.getValue());

        return allLiveData;
    }

    public void Insert(FinalExamQuestion_Model question, FinalExamAnswers_Model answer, int position) {
        if (allLiveData.getValue() == null) {
            allLiveData.setValue(new ArrayList<Model_final_exam_history>());
        }
        List<Model_final_exam_history> modelQuizHistories = new ArrayList<>(allLiveData.getValue());

        for (Model_final_exam_history history : modelQuizHistories) {

            Log.e("Repo_Quiz", " ///// "+question.getExamQuizz_Id()+"**"+history.getQuiz_question_id());

            if (question.getExamQuizz_Id() == history.getQuiz_question_id()) {
                int index = modelQuizHistories.indexOf(history);
                modelQuizHistories.get(index).setQuiz_answer_id(answer.getExamAnswers_Id());
                modelQuizHistories.get(index).setPostion(position);
                modelQuizHistories.get(index).setCorrect_status(answer.getCorrectStatus());
                allLiveData.setValue(modelQuizHistories);
                Log.e("Repo_Quiz", " //* "+question.getExamQuizz_Id()+"//"+answer.getExamAnswers_Id());

                return;
            }
        }
        Model_final_exam_history quizHistory=new Model_final_exam_history(question.getExamQuizz_Id(),answer.getExamAnswers_Id(),question.getExamQuizzName(),answer.getCorrectStatus(),position);
        Log.e("Repo_Quiz", " // "+question.getExamQuizz_Id()+"//"+answer.getExamAnswers_Id());
        modelQuizHistories.add(quizHistory);
        allLiveData.setValue(modelQuizHistories);
    }

    public void ClearHistory(){
        List<Model_final_exam_history> history=new ArrayList<>(allLiveData.getValue());

        history.clear();

        allLiveData.setValue(history);
    }
}

