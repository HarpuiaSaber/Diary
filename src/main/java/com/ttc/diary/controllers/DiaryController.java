package com.ttc.diary.controllers;

import com.ttc.diary.entities.Diary;
import com.ttc.diary.models.DiaryDetailDto;
import com.ttc.diary.models.response.SystemResponse;
import com.ttc.diary.services.FileService;
import com.ttc.diary.utils.Constants;
import com.ttc.diary.models.DiaryDto;
import com.ttc.diary.services.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries")
@PreAuthorize("isAuthenticated()")
public class DiaryController {

    private final DiaryService diaryService;

    private final FileService fileService;

    @Autowired
    public DiaryController(FileService fileService, DiaryService diaryService) {
        this.fileService = fileService;
        this.diaryService = diaryService;
    }

    @PostMapping("/images")
    public List<String> uploadMultipartFile(@RequestParam MultipartFile[] files) {
        return fileService.uploadImage(files, Constants.FOLDER_IMAGE + File.separator + Constants.FOLDER_DIARY);
    }

    @DeleteMapping("/images")
    public String deleteImage(@RequestParam String path){
        return fileService.deleteImage(path);
    }

    @PostMapping
    public ResponseEntity<SystemResponse<DiaryDto>> createDiary(@RequestBody DiaryDto dto) {
        return diaryService.createDiary(dto);
    }

    @PutMapping("/{id}/favorites")
    public ResponseEntity<SystemResponse<Diary>> changeFavoriteStatus(@PathVariable Long id){
        return diaryService.changeFavoriteStatus(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemResponse<DiaryDetailDto>> getDiaryById(@PathVariable Long id){
        return diaryService.getDiaryById(id);
    }
  
    @DeleteMapping("/{id}")
    public ResponseEntity<SystemResponse<Diary>> delete(@PathVariable Long id) {
        return diaryService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemResponse<DiaryDto>> updateDiary(@PathVariable Long id, @RequestBody DiaryDto diaryDto){
        return diaryService.updateDiary(id, diaryDto);
    }
}
