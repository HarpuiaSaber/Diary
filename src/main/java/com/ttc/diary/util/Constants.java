package com.ttc.diary.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class Constants {

    private Constants() {
    }

    public static final String BASE_URL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    public static final String FOLDER_IMAGE = "images";
    public static final String FOLDER_USER = "user";
    public static final String FOLDER_DIARY = "diary";
    public static final String UPLOAD_FOLDER = "C:\\Users\\ztoan\\IdeaProjects\\Diary\\src\\main\\resources\\static";
}
