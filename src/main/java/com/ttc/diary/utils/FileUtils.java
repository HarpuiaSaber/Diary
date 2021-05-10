package com.ttc.diary.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String saveFile(MultipartFile file, String folder) {
        try {
            int index = file.getOriginalFilename().lastIndexOf(".");
            String ext = file.getOriginalFilename().substring(index);
            String name = File.separator + folder + File.separator + System.currentTimeMillis() + ext;

            Path pathAvatar = Paths.get(Constants.UPLOAD_FOLDER + name);
            Files.write(pathAvatar, file.getBytes());
            return name.replace("\\", "/");
        } catch (IOException e) {
            return null;
        }
    }
}
