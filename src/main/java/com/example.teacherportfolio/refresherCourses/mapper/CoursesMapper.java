package com.example.teacherportfolio.refresherCourses.mapper;

import com.example.teacherportfolio.refresherCourses.dto.CoursesRequestDto;
import com.example.teacherportfolio.refresherCourses.dto.CoursesResponseDto;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CoursesMapper {
    public static Course toEntity(CoursesRequestDto dto, Teacher teacher) {
        return Course.builder()
                .courseName(dto.getCourseName())
                .countOfHours(dto.getHours())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .organization(dto.getOrganization())
                .certificateNumber(dto.getCertificateNumber())
                .certificateDate(dto.getCertificateDate())
                .teacher(teacher)
                .build();
    }

    public static CoursesResponseDto toDto(Course course) {
        return CoursesResponseDto.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .hours(course.getCountOfHours())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .organization(course.getOrganization())
                .certificateNumber(course.getCertificateNumber())
                .certificateDate(course.getCertificateDate())
                .teacherId(course.getTeacher().getId())
                .build();
    }
}