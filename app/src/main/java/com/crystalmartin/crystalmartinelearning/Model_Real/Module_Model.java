package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Module_Model {
    private String courseModuleId;
    private String moduleOrderNumber;
    private String courseName;
    private int status;

    public Module_Model() {
    }

    public Module_Model(String courseModuleId, String moduleOrderNumber, String courseName, int status) {
        this.courseModuleId = courseModuleId;
        this.moduleOrderNumber = moduleOrderNumber;
        this.courseName = courseName;
        this.status = status;
    }

    public String getCourseModuleId() {
        return courseModuleId;
    }

    public void setCourseModuleId(String courseModuleId) {
        this.courseModuleId = courseModuleId;
    }

    public String getModuleOrderNumber() {
        return moduleOrderNumber;
    }

    public void setModuleOrderNumber(String moduleOrderNumber) {
        this.moduleOrderNumber = moduleOrderNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
