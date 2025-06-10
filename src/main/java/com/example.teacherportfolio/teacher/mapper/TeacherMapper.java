package com.example.teacherportfolio.teacher.mapper;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.dto.TeacherRequestDto;
import com.example.teacherportfolio.teacher.dto.TeacherResponseDto;
import com.example.teacherportfolio.teacher.dto.TeacherShortResponseDto;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

@UtilityClass
public class TeacherMapper {

    public static TeacherRequestDto toTeacherRequestDto(Teacher teacher) {
        return TeacherRequestDto.builder()
                .lastName(teacher.getLastName())
                .firstName(teacher.getFirstName())
                .surName(teacher.getSurName())
                .birthDate(teacher.getBirthDate())
                .subject(teacher.getSubject())
                .isPartTime(teacher.getIsPartTime())
                .build();
    }

    public static Teacher toTeacher(TeacherRequestDto teacherRequestDto) {
        return Teacher.builder()
                .lastName(teacherRequestDto.getLastName())
                .firstName(teacherRequestDto.getFirstName())
                .surName(teacherRequestDto.getSurName())
                .birthDate(teacherRequestDto.getBirthDate())
                .subject(teacherRequestDto.getSubject())
                .isPartTime(teacherRequestDto.getIsPartTime())
                .build();
    }

    public static TeacherResponseDto toTeacherResponseDto(Teacher teacher) {
        return TeacherResponseDto.builder()
                .id(teacher.getId())
                .lastName(teacher.getLastName())
                .firstName(teacher.getFirstName())
                .surName(teacher.getSurName())
                .birthDate(teacher.getBirthDate())
                .subject(teacher.getSubject())
                .isPartTime(teacher.getIsPartTime())
                .categoryLevels(teacher.getCategories().stream()
                        .map(Category::getCategoryLevel)
                        .collect(Collectors.toList()))
                .educationLevels(teacher.getEducations().stream()
                        .map(Education::getLevelOfEducation)
                        .collect(Collectors.toList()))
                .courseNames(teacher.getCourses().stream()
                        .map(Course::getCourseName)
                        .collect(Collectors.toList()))
                .build();
    }

    public static TeacherShortResponseDto toShortDto(Teacher teacher) {
        return TeacherShortResponseDto.builder()
                .lastName(teacher.getLastName())
                .firstName(teacher.getFirstName())
                .surName(teacher.getSurName())
                .build();
    }
}