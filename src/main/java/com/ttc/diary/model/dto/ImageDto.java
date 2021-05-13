package com.ttc.diary.model.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ImageDto implements Serializable {
    private Long id;
    @NotBlank(message = "Path is mandatory")
    private String path;

    public ImageDto() {
        //default constructor
    }

    public ImageDto(Long id, String path) {
        this.id = id;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
