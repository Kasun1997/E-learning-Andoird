package com.crystalmartin.crystalmartinelearning.Model_Real;

public class FinalExamQuiz_Model {

    private String examQuizz_Id;
    private String examQuizzName;
    private String correctAnswers;

    public FinalExamQuiz_Model() {
    }

    public FinalExamQuiz_Model(String examQuizz_Id, String examQuizzName, String correctAnswers) {
        this.examQuizz_Id = examQuizz_Id;
        this.examQuizzName = examQuizzName;
        this.correctAnswers = correctAnswers;
    }

    public String getExamQuizz_Id() {
        return examQuizz_Id;
    }

    public void setExamQuizz_Id(String examQuizz_Id) {
        this.examQuizz_Id = examQuizz_Id;
    }

    public String getExamQuizzName() {
        return examQuizzName;
    }

    public void setExamQuizzName(String examQuizzName) {
        this.examQuizzName = examQuizzName;
    }

    public String getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(String correctAnswers) {
        this.correctAnswers = correctAnswers;
    }
}
