package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Quiz_Model {
    private String id;
    private String quizName;
    private String noOfAnswerRequired;
    private int status;

    public Quiz_Model() {
    }

    public Quiz_Model(String id, String quizName, String noOfAnswerRequired, int status) {
        this.id = id;
        this.quizName = quizName;
        this.noOfAnswerRequired = noOfAnswerRequired;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getNoOfAnswerRequired() {
        return noOfAnswerRequired;
    }

    public void setNoOfAnswerRequired(String noOfAnswerRequired) {
        this.noOfAnswerRequired = noOfAnswerRequired;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
