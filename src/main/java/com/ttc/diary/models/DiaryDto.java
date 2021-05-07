package com.ttc.diary.models;

import java.io.Serializable;
import java.util.List;

public class DiaryDto implements Serializable {
    private String title;
    private String content;
    private List<ImageDto> imageDtos;
    private List<Long> topicIds;

    public DiaryDto() {
    }

    public DiaryDto(String title, String content, List<ImageDto> imageDtos, List<Long> topicIds) {
        this.title = title;
        this.content = content;
        this.imageDtos = imageDtos;
        this.topicIds = topicIds;
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
