package com.ttc.diary.service.impl;

import com.ttc.diary.model.dto.TopicDto;
import com.ttc.diary.model.entity.Topic;
import com.ttc.diary.model.response.BaseResponse;
import com.ttc.diary.model.response.Response;
import com.ttc.diary.repository.TopicRepository;
import com.ttc.diary.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public List<TopicDto> getAllTopics() {
        return topicRepository.findAll().stream()
                .map(s -> new TopicDto(s.getId(), s.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<BaseResponse<TopicDto>> createTopic(TopicDto dto){
        if (topicRepository.existsByName(dto.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Topic already exists!!!");
        Topic topic = new Topic();
        topic.setId(null);
        topic.setName(dto.getName());
        topicRepository.save(topic);
        dto.setId(topic.getId());

        return Response.ok(dto);
    }
}
