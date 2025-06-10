package com.example.teacherportfolio.refresherCourses.service;


import com.example.teacherportfolio.refresherCourses.dto.CoursesRequestDto;
import com.example.teacherportfolio.refresherCourses.dto.CoursesResponseDto;
import com.example.teacherportfolio.refresherCourses.model.Course;

import java.util.List;

public interface CoursesService {
    List<CoursesResponseDto> getAllCourses();

    CoursesResponseDto getCourseById(Long courseId);

    List<CoursesResponseDto> getCoursesByTeacherId(Long teacherId);

    CoursesResponseDto saveCourseByTeacherId(Long teacherId, CoursesRequestDto refreshCoursesDto);

    CoursesResponseDto updateCoursesByTeacherId(Long teacherId, Course course);

    void deleteCourseByTeacherId(Long teacherId, Long CourseId);

    void deleteAllCoursesByTeacherId(Long teacherId);
}
