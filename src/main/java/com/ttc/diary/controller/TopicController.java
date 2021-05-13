package com.ttc.diary.controller;

import com.ttc.diary.model.dto.TopicDto;
import com.ttc.diary.model.response.BaseResponse;
import com.ttc.diary.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/topics")
@PreAuthorize("isAuthenticated()")
public class TopicController {
    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public List<TopicDto> getAllTopics(){
        return topicService.getAllTopics();
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<BaseResponse<TopicDto>> createTopic(@RequestBody TopicDto dto){
        return topicService.createTopic(dto);
    }
}
