package com.ttc.diary.service;

import com.ttc.diary.model.DiaryDetailDto;
import com.ttc.diary.model.DiaryDto;

import com.ttc.diary.model.entity.Diary;
import org.springframework.http.ResponseEntity;


public interface DiaryService {
    DiaryDto createDiary(DiaryDto dto);
    ResponseEntity<Diary> changeFavoriteStatus(Long id);
    DiaryDetailDto getDiaryById(Long id);
    String delete(Long id);
    DiaryDto updateDiary(Long id, DiaryDto diaryDto);
}
