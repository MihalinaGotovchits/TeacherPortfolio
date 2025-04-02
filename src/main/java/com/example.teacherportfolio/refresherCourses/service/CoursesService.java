package com.example.teacherportfolio.refresherCourses.service;


import com.example.teacherportfolio.refresherCourses.dto.CoursesDto;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;

import java.util.List;
import java.util.UUID;

public interface CoursesService {
    List<CoursesDto> getAllCourses();

    CoursesDto getCourseById(UUID courseId);

    List<CoursesDto> getCoursesByTeacherId(UUID teacherId);

    List<TeacherDtoFull> getTeachersByCourseId(UUID courseId);

    CoursesDto saveCourseByTeacherId(UUID teacherId, CoursesDto refreshCoursesDto);

    CoursesDto updateTeacherCourseByName(UUID teacherId, String nameOfCourse, CoursesDto updateDto);

    CoursesDto updateCoursesByTeacherId(UUID teacherId, CoursesDto refreshCoursesDto);

    void deleteCourseByTeacherId(UUID teacherId, UUID CourseId);

    void deleteAllCoursesByTeacherId(UUID teacherId);
}
