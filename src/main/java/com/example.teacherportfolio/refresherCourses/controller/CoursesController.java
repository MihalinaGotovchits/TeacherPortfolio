package com.example.teacherportfolio.refresherCourses.controller;

import com.example.teacherportfolio.refresherCourses.dto.CoursesDto;
import com.example.teacherportfolio.refresherCourses.service.CoursesService;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/courses")
@Slf4j
public class CoursesController {
    private final CoursesService coursesService;

    @GetMapping
    public List<CoursesDto> getAllCourses() {
        log.info("Получение списка всех курсов повышения квалификации");
        return coursesService.getAllCourses();
    }

    @GetMapping("/{id}")
    public CoursesDto getCourseById(@PathVariable UUID id) {
        log.info("Получение курса повышения квалификации по id");
        return coursesService.getCourseById(id);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<CoursesDto> getCoursesByTeacherId(@PathVariable UUID teacherId) {
        log.info("Получение всех курсов преподавателя с id {}", teacherId);
        return coursesService.getCoursesByTeacherId(teacherId);
    }

    @GetMapping("/{courseId}/teachers")
    public List<TeacherDtoFull> getTeachersByCourseId(@PathVariable UUID courseId) {
        log.info("Получение списка преподавателей с курса {}", courseId);
        return coursesService.getTeachersByCourseId(courseId);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CoursesDto saveCourseByTeacherId(
            @PathVariable UUID teacherId,
            @RequestBody CoursesDto refreshCoursesDto) {
        log.info("Сохранение курса '{}' для преподавателя с Id {} ",
                refreshCoursesDto.getNameOfCourse(), teacherId);
        return coursesService.saveCourseByTeacherId(teacherId, refreshCoursesDto);
    }

    @PutMapping("/teacher/{teacherId}")
    public CoursesDto updateCoursesByTeacherId(
            @PathVariable UUID teacherId,
            @RequestBody CoursesDto refreshCoursesDto) {
        log.info("Обновление курса {} у преподавателя с Id {}", refreshCoursesDto.getNameOfCourse(),
                teacherId);
        return coursesService.updateCoursesByTeacherId(teacherId, refreshCoursesDto);
    }

    @PutMapping("/teacher/{teacherId}/by-name/{nameOfCourse}")
    public CoursesDto updateTeacherCourseByName(
            @PathVariable UUID teacherId,
            @PathVariable String nameOfCourse,
            @RequestBody CoursesDto updateDto) {
        log.info("Обновление курса {} у преподавателя с Id {}", nameOfCourse,
                teacherId);

        return coursesService.updateTeacherCourseByName(teacherId, nameOfCourse, updateDto);
    }

    @DeleteMapping("/teacher/{teacherId}/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourseByTeacherId(
            @PathVariable UUID teacherId,
            @PathVariable UUID courseId) {
        log.info("Удаление курса с Id {} у преподавателя с Id {}", courseId, teacherId);
        coursesService.deleteCourseByTeacherId(teacherId, courseId);
    }

    @DeleteMapping("/teacher/{teacherId}/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCoursesByTeacherId(@PathVariable UUID teacherId) {
        log.info("Удаление всех курсов у преподавателя с Id {}", teacherId);
        coursesService.deleteAllCoursesByTeacherId(teacherId);
    }
}
