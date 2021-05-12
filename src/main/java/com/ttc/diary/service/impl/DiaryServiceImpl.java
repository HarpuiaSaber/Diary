package com.ttc.diary.service.impl;

import com.ttc.diary.exception.ResourceNotFoundException;
import com.ttc.diary.model.*;
import com.ttc.diary.model.entity.Diary;
import com.ttc.diary.model.entity.DiaryImage;
import com.ttc.diary.model.entity.Topic;
import com.ttc.diary.model.entity.User;
import com.ttc.diary.repository.DiaryImageRepository;
import com.ttc.diary.repository.DiaryRepository;
import com.ttc.diary.repository.TopicRepository;
import com.ttc.diary.service.DiaryService;
import com.ttc.diary.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public DiaryDto createDiary(DiaryDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Diary diary = new Diary();
        diary.setOwner(new User(principal.getId()));
        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setFavorite(false);
        List<Topic> topics = topicRepository.findAllById(dto.getTopicIds());
        diary.setTopics(topics);

        diaryRepository.saveAndFlush(diary);
        dto.setId(diary.getId());
        if (dto.getImageDtos() != null && dto.getImageDtos().size() > 0) {
            List<DiaryImage> images = dto.getImageDtos().stream()
                    .map(s -> new DiaryImage(s.getPath(), diary)).collect(Collectors.toList());
            diaryImageRepository.saveAll(images);
            for (int i = 0; i < images.size(); i++) {
                dto.getImageDtos().get(i).setId(images.get(i).getId());
            }
        }
        return dto;
    }

    @Override
    public ResponseEntity<Diary> changeFavoriteStatus(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Can't found diary with id " + id + " !!!"));
        diary.setFavorite(!diary.getFavorite());

        return null;
    }

    @Override
    public DiaryDetailDto getDiaryById(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Can't found diary with id " + id + " !!!"));

        DiaryDetailDto diaryDetailDto = new DiaryDetailDto();

        diaryDetailDto.setId(diary.getId());
        diaryDetailDto.setTopics(diary.getTopics().stream()
                .map(s -> new TopicDto(s.getId(), s.getName()))
                .collect(Collectors.toList()));
        diaryDetailDto.setTitle(diary.getTitle());
        diaryDetailDto.setContent(diary.getContent());
        diaryDetailDto.setImages(diaryImageRepository.findAllByDiaryId(id).stream()
                .map(s -> new ImageDto(s.getId(), s.getPath()))
                .collect(Collectors.toList()));
        diaryDetailDto.setCreationTime(diary.getCreationTime());
        diaryDetailDto.setModificationTime(diary.getModificationTime());

        return diaryDetailDto;
    }

    @Override
    public String delete(Long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Diary not found!!!"));
        List<DiaryImage> images = diaryImageRepository.findAllByDiaryId(id);
        for (DiaryImage image : images) {
            try {
                Files.deleteIfExists(Paths.get(Constants.BASE_URL + image.getPath()));
            } catch (IOException e) {
            }
        }
        diaryImageRepository.deleteInBatch(images);

        diaryRepository.delete(diary);
        return "Delete success";
    }

    @Override
    public DiaryDto updateDiary(Long id, DiaryDto diaryDto) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Diary not exist with id " + id));
        diary.setTitle(diaryDto.getTitle());
        diary.setContent(diaryDto.getContent());

        diary.setTopics(topicRepository.findAllById(diaryDto.getTopicIds()));

        if (diaryDto.getImageDtos() != null && diaryDto.getImageDtos().size() > 0) {
            List<Long> imageIds = diaryDto.getImageDtos().stream().map(ImageDto::getId).collect(Collectors.toList());
            List<DiaryImage> diaryImages = diaryImageRepository.findAllByDiaryId(id);

            List<DiaryImage> diaryOldImages = diaryImages.stream()
                    .filter(s -> !imageIds.contains(s.getId()))
                    .collect(Collectors.toList());
            diaryImageRepository.deleteInBatch(diaryOldImages);

            List<DiaryImage> diaryNewImages = diaryDto.getImageDtos().stream()
                    .filter(s -> s.getId() == null || (s.getId() != null && s.getId() == 0))
                    .map(s -> new DiaryImage(s.getPath(), diary))
                    .collect(Collectors.toList());
            diaryImageRepository.saveAll(diaryNewImages);
            for (int i = 0; i < diaryNewImages.size(); i++) {
                diaryDto.getImageDtos().get(i).setId(diaryNewImages.get(i).getId());
            }
        }

        return diaryDto;
    }
}
