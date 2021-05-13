package com.ttc.diary.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class TopicDto implements Serializable {
    @Min(value = 1, message = "id can not be less than 1")
    private long id;
    @NotBlank(message = "Topic is mandatory")
    private String name;

    public TopicDto() {
        //default constructor
    }

    public TopicDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
