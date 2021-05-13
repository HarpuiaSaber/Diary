package com.ttc.diary.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class DiaryDto implements Serializable {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    @NotBlank(message = "content is mandatory")
    private String content;
    private List<ImageDto> imageDtos;
    @NotNull(message = "Topic may not be null")
    private List<Long> topicIds;

    public DiaryDto() {
        //default constructor
    }

    public DiaryDto(String title, String content, List<ImageDto> imageDtos, List<Long> topicIds) {
        this.title = title;
        this.content = content;
        this.imageDtos = imageDtos;
        this.topicIds = topicIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<ImageDto> getImageDtos() {
        return imageDtos;
    }

    public void setImageDtos(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
    }

    public List<Long> getTopicIds() {
        return topicIds;
    }

    public void setTopicIds(List<Long> topicIds) {
        this.topicIds = topicIds;
    }
}
