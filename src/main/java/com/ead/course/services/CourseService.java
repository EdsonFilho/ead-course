package com.ead.course.services;

import com.ead.course.models.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    CourseModel save(CourseModel courseModel);

    Optional<CourseModel> findById(UUID courseId);

    void delete(CourseModel courseModel);

    Page<CourseModel> findAll(Pageable pageable);
}
