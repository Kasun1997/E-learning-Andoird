package com.crystalmartin.crystalmartinelearning.Model_Real;

public class UserMain_Model {
    private UserDetail_Model user;
    private String token;

    public UserMain_Model() {
    }

    public UserMain_Model(UserDetail_Model user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDetail_Model getUser() {
        return user;
    }

    public void setUser(UserDetail_Model user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
