package com.crystalmartin.crystalmartinelearning.Model_Sub;

public class Rating_M_Get {

    private String id;
    private String status;

    public Rating_M_Get(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
