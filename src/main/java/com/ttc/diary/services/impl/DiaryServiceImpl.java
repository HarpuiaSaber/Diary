package com.ttc.diary.services.impl;

import com.ttc.diary.entities.Topic;
import com.ttc.diary.models.TopicDto;
import com.ttc.diary.repositories.DiaryImageRepository;
import com.ttc.diary.repositories.DiaryRepository;
import com.ttc.diary.repositories.TopicRepository;
import com.ttc.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    private final TopicRepository topicRepository;

    private final DiaryImageRepository diaryImageRepository;

    @Autowired
    public DiaryServiceImpl(DiaryRepository diaryRepository, TopicRepository topicRepository, DiaryImageRepository diaryImageRepository) {
        this.diaryRepository = diaryRepository;
        this.topicRepository = topicRepository;
        this.diaryImageRepository = diaryImageRepository;
    }
}
