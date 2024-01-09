package com.crystalmartin.crystalmartinelearning.Model_Real;

public class Video_Model {

    private String id;
    private String vedioName;
    private String videoPath;
    private String videolength;
    private String videoOrder;

    public Video_Model() {
    }

    public Video_Model(String id, String vedioName, String videoPath, String videolength, String videoOrder) {
        this.id = id;
        this.vedioName = vedioName;
        this.videoPath = videoPath;
        this.videolength = videolength;
        this.videoOrder = videoOrder;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVedioName() {
        return vedioName;
    }

    public void setVedioName(String vedioName) {
        this.vedioName = vedioName;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideolength() {
        return videolength;
    }

    public void setVideolength(String videolength) {
        this.videolength = videolength;
    }

    public String getVideoOrder() {
        return videoOrder;
    }

    public void setVideoOrder(String videoOrder) {
        this.videoOrder = videoOrder;
    }
}
