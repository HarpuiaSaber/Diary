package com.ttc.diary.services;

import com.ttc.diary.models.DiaryDto;

import com.ttc.diary.entities.Diary;
import org.springframework.http.ResponseEntity;

public interface DiaryService {
    DiaryDto createDiary(DiaryDto dto);
    ResponseEntity<Diary> changeFavoriteStatus(Long id, Boolean isFavorite);
     String delete(Long id);
}