package com.ttc.diary.controllers;

import com.ttc.diary.entities.Topic;
import com.ttc.diary.models.TopicDto;
import com.ttc.diary.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
