package com.ttc.diary.models;

import java.io.Serializable;

public class UserDetailDto implements Serializable {
    private Long id;
    private String name;
    private String username;
    private String avatar;

    public UserDetailDto() {
    }

    public UserDetailDto(Long id, String name, String username, String avatar) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
