package com.crystalmartin.crystalmartinelearning.Model_Real;

public class FinalExamAvailability_Model {

    private String trainingCourse_Id;
    private String examName;
    private String trainingExam_Id;
    private String trainingSheduleExam_Date;
    private String trainingSheduleExam_StartTime;
    private String trainingSheduleExam_EndTime;
    private String trainingLocation_Name;
    private Boolean online;

    public FinalExamAvailability_Model() {
    }

    public FinalExamAvailability_Model(String trainingCourse_Id, String examName, String trainingExam_Id, String trainingSheduleExam_Date, String trainingSheduleExam_StartTime, String trainingSheduleExam_EndTime, String trainingLocation_Name, Boolean online) {
        this.trainingCourse_Id = trainingCourse_Id;
        this.examName = examName;
        this.trainingExam_Id = trainingExam_Id;
        this.trainingSheduleExam_Date = trainingSheduleExam_Date;
        this.trainingSheduleExam_StartTime = trainingSheduleExam_StartTime;
        this.trainingSheduleExam_EndTime = trainingSheduleExam_EndTime;
        this.trainingLocation_Name = trainingLocation_Name;
        this.online = online;
    }

    public String getTrainingCourse_Id() {
        return trainingCourse_Id;
    }

    public void setTrainingCourse_Id(String trainingCourse_Id) {
        this.trainingCourse_Id = trainingCourse_Id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getTrainingExam_Id() {
        return trainingExam_Id;
    }

    public void setTrainingExam_Id(String trainingExam_Id) {
        this.trainingExam_Id = trainingExam_Id;
    }

    public String getTrainingSheduleExam_Date() {
        return trainingSheduleExam_Date;
    }

    public void setTrainingSheduleExam_Date(String trainingSheduleExam_Date) {
        this.trainingSheduleExam_Date = trainingSheduleExam_Date;
    }

    public String getTrainingSheduleExam_StartTime() {
        return trainingSheduleExam_StartTime;
    }

    public void setTrainingSheduleExam_StartTime(String trainingSheduleExam_StartTime) {
        this.trainingSheduleExam_StartTime = trainingSheduleExam_StartTime;
    }

    public String getTrainingSheduleExam_EndTime() {
        return trainingSheduleExam_EndTime;
    }

    public void setTrainingSheduleExam_EndTime(String trainingSheduleExam_EndTime) {
        this.trainingSheduleExam_EndTime = trainingSheduleExam_EndTime;
    }

    public String getTrainingLocation_Name() {
        return trainingLocation_Name;
    }

    public void setTrainingLocation_Name(String trainingLocation_Name) {
        this.trainingLocation_Name = trainingLocation_Name;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }
}
