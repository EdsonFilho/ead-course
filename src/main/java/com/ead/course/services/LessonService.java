package com.ead.course.services;

import com.ead.course.models.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public interface LessonService {

    LessonModel save(LessonModel lessonModel);

    Optional<LessonModel> findById(UUID lessonId);

    void delete(LessonModel lessonModel);

    Page<LessonModel> findAll(Pageable pageable);
}
