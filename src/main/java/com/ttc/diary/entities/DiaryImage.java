package com.ttc.diary.entities;

import javax.persistence.*;

@Entity
@Table(name = "diary_image")
public class DiaryImage extends BaseEntity<Long> {
    private String path;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;

    public DiaryImage() {
        //default constructor
    }

    public DiaryImage(Long id) {
        super(id);
    }

    public DiaryImage(String path, Diary diary) {
        this.path = path;
        this.diary = diary;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Diary getDiary() {
        return diary;
    }

    public void setDiary(Diary diary) {
        this.diary = diary;
    }
}
