package com.crystalmartin.crystalmartinelearning.Model_Real;

public class QuizAnswer_Model {
    private String id;
    private String answersId;
    private String correctStatus;
    private String name;

    public QuizAnswer_Model() {
    }

    public QuizAnswer_Model(String id, String answersId, String correctStatus, String name) {
        this.id = id;
        this.answersId = answersId;
        this.correctStatus = correctStatus;
        this.name = name;
    }

    public QuizAnswer_Model(String answersId, String correctStatus) {
        this.answersId = answersId;
        this.correctStatus = correctStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnswersId() {
        return answersId;
    }

    public void setAnswersId(String answersId) {
        this.answersId = answersId;
    }

    public String getCorrectStatus() {
        return correctStatus;
    }

    public void setCorrectStatus(String correctStatus) {
        this.correctStatus = correctStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
