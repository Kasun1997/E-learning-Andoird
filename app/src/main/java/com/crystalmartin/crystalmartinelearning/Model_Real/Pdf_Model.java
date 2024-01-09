package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Pdf_Model {
    private String id;
    private String notesName;
    private String notesPath;
    private String noteslength;
    private String notesOrder;

    public Pdf_Model() {
    }

    public Pdf_Model(String id, String notesName, String notesPath, String noteslength, String notesOrder) {
        this.id = id;
        this.notesName = notesName;
        this.notesPath = notesPath;
        this.noteslength = noteslength;
        this.notesOrder = notesOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotesName() {
        return notesName;
    }

    public void setNotesName(String notesName) {
        this.notesName = notesName;
    }

    public String getNotesPath() {
        return notesPath;
    }

    public void setNotesPath(String notesPath) {
        this.notesPath = notesPath;
    }

    public String getNoteslength() {
        return noteslength;
    }

    public void setNoteslength(String noteslength) {
        this.noteslength = noteslength;
    }

    public String getNotesOrder() {
        return notesOrder;
    }

    public void setNotesOrder(String notesOrder) {
        this.notesOrder = notesOrder;
    }
}
