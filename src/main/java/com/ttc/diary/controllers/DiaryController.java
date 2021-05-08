package com.ttc.diary.controllers;

import com.ttc.diary.services.FileService;
import com.ttc.diary.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/v1/diaries")
@PreAuthorize("isAuthenticated()")
public class DiaryController {

    private final FileService fileService;

    @Autowired
    public DiaryController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public List<String> uploadMultipartFile(@RequestParam MultipartFile[] files) {
        return fileService.uploadImage(files, Constants.FOLDER_IMAGE + File.separator + Constants.FOLDER_DIARY);
    }

    @DeleteMapping("/images")
    public String deleteImage(@RequestParam String path){
        return fileService.deleteImage(path);
    }
}
