package com.ttc.diary.service;

import com.ttc.diary.model.dto.TopicDto;
import com.ttc.diary.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TopicService {
    public List<TopicDto> getAllTopics();
    ResponseEntity<BaseResponse<TopicDto>> createTopic(TopicDto dto);
}
