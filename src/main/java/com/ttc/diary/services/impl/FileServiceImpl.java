package com.ttc.diary.services.impl;

import com.ttc.diary.services.FileService;
import com.ttc.diary.utils.Constants;
import com.ttc.diary.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public List<String> uploadImage(MultipartFile[] files, String folder) {
        List<String> paths = new ArrayList<>();
        for (MultipartFile file : files) {
            paths.add(Constants.BASE_URL + "/" + FileUtils.saveFile(file, folder));
        }
        return paths;
    }

    @Override
    public String deleteImage(String path) {
        String imageName = path.replace(Constants.BASE_URL, Constants.UPLOAD_FOLDER);
        try {
            Files.deleteIfExists(Paths.get(imageName));
            return "Deleted";
        } catch (IOException e) {
            return "Delete fail";
        }
    }
}
