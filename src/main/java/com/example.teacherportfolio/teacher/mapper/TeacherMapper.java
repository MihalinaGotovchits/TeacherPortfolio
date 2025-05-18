package com.example.teacherportfolio.teacher.mapper;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.dto.TeacherDtoShort;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TeacherMapper {
    public static TeacherDtoFull toTeacherDtoFull(Teacher teacher) {
        return TeacherDtoFull.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .name(teacher.getName())
                .surName(teacher.getSurName())
                .dateOfBirth(teacher.getDateOfBirth())
                .subject(teacher.getSubject())
                .workExperience(teacher.getWorkExperience())
                .education(teacher.getEducation())
                .category(teacher.getCategory())
                .refresherCoursesList(teacher.getCourses())
                .isPartTime(teacher.getIsPartTime())
                .build();
    }

    public static Teacher toTeacher(TeacherDtoFull teacherDtoFull) {
        return Teacher.builder()
                .id(teacherDtoFull.getId())
                .firstName(teacherDtoFull.getFirstName())
                .name(teacherDtoFull.getName())
                .surName(teacherDtoFull.getSurName())
                .dateOfBirth(teacherDtoFull.getDateOfBirth())
                .subject(teacherDtoFull.getSubject())
                .workExperience(teacherDtoFull.getWorkExperience())
                .education(teacherDtoFull.getEducation())
                .category(teacherDtoFull.getCategory())
                .courses(teacherDtoFull.getRefresherCoursesList())
                .isPartTime(teacherDtoFull.getIsPartTime())
                .build();
    }

    public static TeacherDtoShort toTeacherDtoShort(Teacher teacher) {
        return TeacherDtoShort.builder()
                .id(teacher.getId())
                .firstName(teacher.getFirstName())
                .name(teacher.getName())
                .surName(teacher.getSurName())
                .build();
    }
}
