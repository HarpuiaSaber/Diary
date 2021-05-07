package com.ttc.diary.controllers;

import com.ttc.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return diaryService.delete(id);
    }
}
