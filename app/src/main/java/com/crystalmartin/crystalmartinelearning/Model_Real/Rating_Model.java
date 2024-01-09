package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Rating_Model {

    private String feedbackId;
    private String rating;

    public Rating_Model() {
    }

    public Rating_Model(String feedbackId, String rating) {
        this.feedbackId = feedbackId;
        this.rating = rating;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
