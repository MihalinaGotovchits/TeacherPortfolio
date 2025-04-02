package com.example.teacherportfolio.teacher.dto;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.teacher.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class TeacherDtoFull {
    private UUID id;

    private String firstName;

    private String name;

    private String surName;

    private LocalDate dateOfBirth;

    private Subject subject;

    private int workExperience;

    private List<Education> education;

    private Category category;

    private List<Course> refresherCoursesList;
}
