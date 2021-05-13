package com.ttc.diary.service.impl;

import com.ttc.diary.model.dto.TopicDto;
import com.ttc.diary.repository.TopicRepository;
import com.ttc.diary.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
