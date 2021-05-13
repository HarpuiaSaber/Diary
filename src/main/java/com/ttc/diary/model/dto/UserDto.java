package com.ttc.diary.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class UserDto implements Serializable {
    private Long id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;

    public UserDto() {
        //default constructor
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
