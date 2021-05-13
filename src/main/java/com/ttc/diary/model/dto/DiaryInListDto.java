package com.ttc.diary.model.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class DiaryInListDto implements Serializable {
    private long id;
    private String title;
    private OffsetDateTime creationTime;
    private OffsetDateTime modificationTime;
    private boolean isFavorite;
    private List<TopicDto> topics;

    public DiaryInListDto() {
        //Default constructor
    }

    public DiaryInListDto(long id, String title, OffsetDateTime creationTime, OffsetDateTime modificationTime, boolean isFavorite, List<TopicDto> topics) {
        this.id = id;
        this.title = title;
        this.creationTime = creationTime;
        this.modificationTime = modificationTime;
        this.isFavorite = isFavorite;
        this.topics = topics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public List<TopicDto> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicDto> topics) {
        this.topics = topics;
    }
}
