package com.crystalmartin.crystalmartinelearning.Model_Real;

public class UserDetail_Model {
    private int userId;
    private String username;
    private String epfNo;
    private String imageUrl;
    private String profileName;

    public UserDetail_Model() {
    }

    public UserDetail_Model(int userId, String username, String epfNo, String imageUrl, String profileName) {
        this.userId = userId;
        this.username = username;
        this.epfNo = epfNo;
        this.imageUrl = imageUrl;
        this.profileName = profileName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEpfNo() {
        return epfNo;
    }

    public void setEpfNo(String epfNo) {
        this.epfNo = epfNo;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
}
