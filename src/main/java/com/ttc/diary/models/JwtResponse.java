package com.ttc.diary.models;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private UserDetailDto user;
    private String token;
    private String type = "Bearer";

    public JwtResponse() {
        //default constructor
    }

    public JwtResponse(UserDetailDto user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDetailDto getUser() {
        return user;
    }

    public void setUser(UserDetailDto user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
