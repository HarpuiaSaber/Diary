package com.ttc.diary.repository;

import com.ttc.diary.model.entity.DiaryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryImageRepository extends JpaRepository<DiaryImage, Long> {

    List<DiaryImage> findAllByDiaryId(Long diaryId);
}
