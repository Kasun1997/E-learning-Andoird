package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Class_Model {
    private String name;
    private String trainingSheduleId;

    public Class_Model() {
    }

    public Class_Model(String name, String trainingSheduleId) {
        this.name = name;
        this.trainingSheduleId = trainingSheduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainingSheduleId() {
        return trainingSheduleId;
    }

    public void setTrainingSheduleId(String trainingSheduleId) {
        this.trainingSheduleId = trainingSheduleId;
    }
}
