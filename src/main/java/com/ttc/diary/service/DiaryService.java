package com.ttc.diary.service;

import com.ttc.diary.entities.Diary;
import com.ttc.diary.models.DiaryDetailDto;
import com.ttc.diary.models.DiaryDto;
import com.ttc.diary.model.DiaryDetailDto;
import com.ttc.diary.model.DiaryDto;

import com.ttc.diary.models.response.SystemResponse;
import com.ttc.diary.model.entity.Diary;
import org.springframework.http.ResponseEntity;


public interface DiaryService {
    ResponseEntity<SystemResponse<DiaryDto>> createDiary(DiaryDto dto);
    ResponseEntity<SystemResponse<Diary>> changeFavoriteStatus(Long id);
    ResponseEntity<SystemResponse<DiaryDetailDto>> getDiaryById(Long id);
    ResponseEntity<SystemResponse<Diary>> delete(Long id);
    ResponseEntity<SystemResponse<DiaryDto>> updateDiary(Long id, DiaryDto diaryDto);
}
