package com.ead.course.controllers;

import com.ead.course.dtos.LessonDto;
import com.ead.course.models.LessonModel;
import com.ead.course.services.LessonService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/module/{moduleId}/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonCotroller {

    @Autowired
    LessonService lessonService;

    @PostMapping
    public ResponseEntity<Object> saveLesson(@PathVariable("moduleId") UUID moduleId, @RequestBody @Valid LessonDto lessonDto){
        var lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonDto, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.save(lessonModel));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable("lessonId") UUID lessonId){
        Optional<LessonModel> lessonModelOptional = lessonService.findById(lessonId);
        if (!lessonModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("lesson not found");
        } else {
            lessonService.delete(lessonModelOptional.get());
            return ResponseEntity.status(HttpStatus.OK).body("lesson deleted");
        }
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable("moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId, @RequestBody @Valid LessonDto lessonDto) {
        Optional<LessonModel> lessonModelOptional = lessonService.findById(lessonId);
        if (!lessonModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("lesson not found");
        }
        var lessonModel = lessonModelOptional.get();
        lessonModel.setTitle(lessonDto.getTitle());
        lessonModel.setDescription(lessonDto.getDescription());
        lessonModel.setVideoUrl(lessonDto.getVideoUrl());
        lessonModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.save(lessonModel));
    }

    @GetMapping
    public ResponseEntity<Object> findAllLessons(@PathVariable("moduleId") UUID moduleId, @PageableDefault(sort = "lessonId") Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.findAll(pageable));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<Object> findOneLesson(@PathVariable(value = "lessonId") UUID lessonId){
        Optional<LessonModel> lessonModelOptional = lessonService.findById(lessonId);
        if (!lessonModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("lesson not found");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(lessonModelOptional.get());
        }
    }
}
