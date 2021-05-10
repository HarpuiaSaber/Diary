package com.ttc.diary.services;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<String> uploadImage(MultipartFile[] files, String folder);
    String deleteImage(String path);
}
