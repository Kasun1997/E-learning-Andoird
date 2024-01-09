package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Training_Model {

    private String trainingName;
    private int trainingId;
    private int status;

    public Training_Model() {
    }

    public Training_Model(String trainingName, int trainingId, int status) {
        this.trainingName = trainingName;
        this.trainingId = trainingId;
        this.status = status;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public int getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
