package com.ttc.diary.repository;

import com.ttc.diary.model.entity.Diary;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, JpaSpecificationExecutor<Diary> {

    @EntityGraph(attributePaths = {"topics"})
    Stream<Diary> findDistinctByIdIn(Iterable<Long> id);
}
