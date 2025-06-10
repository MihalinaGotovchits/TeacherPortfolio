package com.example.teacherportfolio.refresherCourses.controller;

import com.example.teacherportfolio.refresherCourses.dto.CoursesRequestDto;
import com.example.teacherportfolio.refresherCourses.dto.CoursesResponseDto;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.refresherCourses.service.CoursesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("courses")
@Slf4j
public class CoursesController {
    private final CoursesService coursesService;

    @GetMapping
    public List<CoursesResponseDto> getAllCourses() {
        log.info("Получение списка всех курсов повышения квалификации");
        return coursesService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CoursesResponseDto getCourseById(@PathVariable Long id) {
        log.info("Получение курса повышения квалификации по id");
        return coursesService.getCourseById(id);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<CoursesResponseDto> getCoursesByTeacherId(@PathVariable Long teacherId) {
        log.info("Получение всех курсов преподавателя с id {}", teacherId);
        return coursesService.getCoursesByTeacherId(teacherId);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CoursesResponseDto saveCourseByTeacherId(
            @PathVariable Long teacherId,
            @RequestBody CoursesRequestDto refreshCoursesDto) {
        log.info("Сохранение курса '{}' для преподавателя с Id {} ",
                refreshCoursesDto.getCourseName(), teacherId);
        return coursesService.saveCourseByTeacherId(teacherId, refreshCoursesDto);
    }

    @PatchMapping("/teacher/{teacherId}")
    public CoursesResponseDto updateCoursesByTeacherId(
            @PathVariable Long teacherId,
            @Valid @RequestBody Course refreshCoursesDto) {
        log.info("Обновление курса {} у преподавателя с Id {}", refreshCoursesDto.getCourseName(),
                teacherId);
        return coursesService.updateCoursesByTeacherId(teacherId, refreshCoursesDto);
    }

    @DeleteMapping("/teacher/{teacherId}/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseByTeacherId(
            @PathVariable Long teacherId,
            @PathVariable Long courseId) {
        log.info("Удаление курса с Id {} у преподавателя с Id {}", courseId, teacherId);
        coursesService.deleteCourseByTeacherId(teacherId, courseId);
    }

    @DeleteMapping("/teacher/{teacherId}/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCoursesByTeacherId(@PathVariable Long teacherId) {
        log.info("Удаление всех курсов у преподавателя с Id {}", teacherId);
        coursesService.deleteAllCoursesByTeacherId(teacherId);
    }
}
