package com.ttc.diary.controllers;

import com.ttc.diary.models.DiaryDto;
import com.ttc.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public DiaryDto createDiary(@RequestBody DiaryDto dto) {
        return diaryService.createDiary(dto);
    }
}
