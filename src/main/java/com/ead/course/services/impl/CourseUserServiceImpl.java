package com.ead.course.services.impl;

import com.ead.course.client.AuthUserClient;
import com.ead.course.models.CourseModel;
import com.ead.course.models.CourseUserModel;
import com.ead.course.repositories.CourseUserRepository;
import com.ead.course.services.CourseUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseUserServiceImpl implements CourseUserService {

    @Autowired
    CourseUserRepository courseUserRepository;

    @Autowired
    AuthUserClient authUserClient;

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId){
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);
    }

    @Override
    public boolean existsByCourseId(UUID courseId){
        return courseUserRepository.existsByCourseCourseId(courseId);
    }

    @Override
    public CourseUserModel save(CourseUserModel courseUserModel){
        return courseUserRepository.save(courseUserModel);
    }


    @Override
    @Transactional
    public CourseUserModel saveAndSendSubscriptionUserInCourse(CourseUserModel courseUserModel) {
        // Saves in local DB
        courseUserModel = courseUserRepository.save(courseUserModel);

        authUserClient.postSubscriptionUserInCourse(courseUserModel.getCourse().getCourseId(), courseUserModel.getUserId());

        return courseUserModel;
    }
}
