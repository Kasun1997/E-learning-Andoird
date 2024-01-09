package com.crystalmartin.crystalmartinelearning.Model_Real;

public class FinalExamAnswers_Model {
    private String examAnswers_Id;
    private String examAnswersName;
    private String correctStatus;

    public FinalExamAnswers_Model() {
    }

    public FinalExamAnswers_Model(String examAnswers_Id, String examAnswersName, String correctStatus) {
        this.examAnswers_Id = examAnswers_Id;
        this.examAnswersName = examAnswersName;
        this.correctStatus = correctStatus;
    }

    public FinalExamAnswers_Model(String examAnswers_Id, String correctStatus) {
        this.examAnswers_Id = examAnswers_Id;
        this.correctStatus = correctStatus;
    }

    public String getExamAnswers_Id() {
        return examAnswers_Id;
    }

    public void setExamAnswers_Id(String examAnswers_Id) {
        this.examAnswers_Id = examAnswers_Id;
    }

    public String getExamAnswersName() {
        return examAnswersName;
    }

    public void setExamAnswersName(String examAnswersName) {
        this.examAnswersName = examAnswersName;
    }

    public String getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(String correctStatus) {
        this.correctStatus = correctStatus;
    }
}
