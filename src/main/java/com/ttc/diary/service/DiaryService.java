package com.ttc.diary.service;

import com.ttc.diary.model.dto.DiaryDetailDto;
import com.ttc.diary.model.dto.DiaryDto;

import com.ttc.diary.model.response.BaseResponse;
import com.ttc.diary.model.entity.Diary;
import org.springframework.http.ResponseEntity;


public interface DiaryService {
    ResponseEntity<BaseResponse<DiaryDto>> createDiary(DiaryDto dto);
    ResponseEntity<BaseResponse<Diary>> changeFavoriteStatus(Long id);
    ResponseEntity<BaseResponse<DiaryDetailDto>> getDiaryById(Long id);
    ResponseEntity<BaseResponse<Diary>> delete(Long id);
    ResponseEntity<BaseResponse<DiaryDto>> updateDiary(Long id, DiaryDto diaryDto);
}
