package com.ttc.diary.services.impl;

import com.ttc.diary.entities.Diary;
import com.ttc.diary.entities.User;
import com.ttc.diary.exception.ResourceNotFoundException;
import com.ttc.diary.models.DiaryDto;
import com.ttc.diary.models.UserPrincipal;
import com.ttc.diary.repositories.DiaryImageRepository;
import com.ttc.diary.repositories.DiaryRepository;
import com.ttc.diary.repositories.TopicRepository;
import com.ttc.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

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

    @Override
    public DiaryDto createDiary(DiaryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Diary diary = new Diary();
        diary.setOwner(new User(principal.getId()));

        diaryRepository.saveAndFlush(diary);


        return dto;
    }

    @Override
    public DiaryDto updateDiary(Long id, DiaryDto diaryDto) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diary not exist with id " + id));
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());
        diary.getTopics().stream().map(s->s.getId()).collect(Collectors.toList());
        diaryDto.getTopicIds();
        return diaryDto;
    }
}
