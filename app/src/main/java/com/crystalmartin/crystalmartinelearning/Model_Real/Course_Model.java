package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Course_Model {

    private int userId;
    private String course_Name;
    private int course_Id;
    private int status;
    public Course_Model() {
    }

    public Course_Model(int userId, String course_Name, int course_Id, int status) {
        this.userId = userId;
        this.course_Name = course_Name;
        this.course_Id = course_Id;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCourse_Name() {
        return course_Name;
    }

    public void setCourse_Name(String course_Name) {
        this.course_Name = course_Name;
    }

    public int getCourse_Id() {
        return course_Id;
    }

    public void setCourse_Id(int course_Id) {
        this.course_Id = course_Id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
