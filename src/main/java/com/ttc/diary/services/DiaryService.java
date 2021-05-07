package com.ttc.diary.services;

import com.ttc.diary.models.DiaryDto;

public interface DiaryService {
    DiaryDto createDiary(DiaryDto dto);

    DiaryDto updateDiary(Long id, DiaryDto diaryDto);
}
