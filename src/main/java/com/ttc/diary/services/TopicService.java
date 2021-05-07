package com.ttc.diary.services;

import com.ttc.diary.entities.Topic;
import com.ttc.diary.models.TopicDto;

import java.util.List;

public interface TopicService {
    public List<TopicDto> getAllTopics();
}
