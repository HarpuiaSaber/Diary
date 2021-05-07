package com.ttc.diary.services.impl;

import com.ttc.diary.entities.Diary;
import com.ttc.diary.entities.DiaryImage;
import com.ttc.diary.repositories.DiaryImageRepository;
import com.ttc.diary.repositories.DiaryRepository;
import com.ttc.diary.repositories.TopicRepository;
import com.ttc.diary.services.DiaryService;
import com.ttc.diary.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
}
