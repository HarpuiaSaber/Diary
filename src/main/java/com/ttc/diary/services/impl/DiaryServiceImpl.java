package com.ttc.diary.services.impl;

import com.ttc.diary.entities.Diary;
import com.ttc.diary.entities.DiaryImage;
import com.ttc.diary.entities.Topic;
import com.ttc.diary.entities.User;
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
import java.util.List;
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
        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setFavorite(false);
        diary.setTopics(dto.getTopicIds().stream().map(s -> new Topic(s)).collect(Collectors.toList()));

        diaryRepository.saveAndFlush(diary);

        List<DiaryImage> images = dto.getImageDtos().stream()
                .map(s-> new DiaryImage(s.getPath(), diary)).collect(Collectors.toList());
        diaryImageRepository.saveAll(images);

        return dto;
    }

}
