package com.ttc.diary.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class DiaryDetailDto implements Serializable {
    private Long id;
    private String title;
    private String content;
    private OffsetDateTime creationTime;
    private OffsetDateTime modificationTime;
    private List<ImageDto> images;
    private List<TopicDto> topics;

    public DiaryDetailDto() {
        //default constructor
    }

    public DiaryDetailDto(Long id, String title, String content, OffsetDateTime creationTime, OffsetDateTime modificationTime, List<ImageDto> images, List<TopicDto> topics) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.images = images;
        this.topics = topics;
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

    public OffsetDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(OffsetDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public OffsetDateTime getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(OffsetDateTime modificationTime) {
        this.modificationTime = modificationTime;
    }

    public List<ImageDto> getImages() {
        return images;
    }

    public void setImages(List<ImageDto> images) {
        this.images = images;
    }

    public List<TopicDto> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDto> topics) {
        this.topics = topics;
    }
}
