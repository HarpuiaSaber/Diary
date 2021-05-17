package com.ttc.diary.controller;


import com.ttc.diary.model.dto.DiaryInListDto;
import com.ttc.diary.model.entity.Diary;
import com.ttc.diary.model.dto.DiaryDetailDto;
import com.ttc.diary.model.response.BaseResponse;
import com.ttc.diary.model.response.GridResult;
import com.ttc.diary.service.FileService;
import com.ttc.diary.util.Constants;
import com.ttc.diary.model.dto.DiaryDto;
import com.ttc.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    public String deleteImage(@RequestParam String path) {
        return fileService.deleteImage(path);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<DiaryDto>> createDiary(@RequestBody @Valid DiaryDto dto) {
        return diaryService.createDiary(dto);
    }

    @PutMapping("/{id}/favorites")
    public ResponseEntity<BaseResponse<Diary>> changeFavoriteStatus(@PathVariable Long id) {
        return diaryService.changeFavoriteStatus(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<DiaryDetailDto>> getDiaryById(@PathVariable Long id) {
        return diaryService.getDiaryById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Diary>> delete(@PathVariable Long id) {
        return diaryService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<DiaryDto>> updateDiary(@PathVariable Long id, @RequestBody @Valid DiaryDto diaryDto) {
        return diaryService.updateDiary(id, diaryDto);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<GridResult<DiaryInListDto>>> searchWithPaging(@RequestParam String param) {
        return diaryService.searchWithPaging(param);
    }
}