package com.crystalmartin.crystalmartinelearning.Model_Real;

public class QuizQuestion_Model {

    private String id;
    private String name;


    public QuizQuestion_Model() {
    }

    public QuizQuestion_Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
