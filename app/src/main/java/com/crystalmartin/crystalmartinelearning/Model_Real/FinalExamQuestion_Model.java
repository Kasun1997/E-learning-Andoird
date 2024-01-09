package com.crystalmartin.crystalmartinelearning.Model_Real;

public class FinalExamQuestion_Model {

    private String examQuizz_Id;
    private String examQuizzName;

    public FinalExamQuestion_Model() {
    }

    public FinalExamQuestion_Model(String examQuizz_Id, String examQuizzName) {
        this.examQuizz_Id = examQuizz_Id;
        this.examQuizzName = examQuizzName;
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
}
