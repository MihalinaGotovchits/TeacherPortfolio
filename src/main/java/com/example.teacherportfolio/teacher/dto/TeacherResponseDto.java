package com.example.teacherportfolio.teacher.dto;

import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.teacher.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class TeacherResponseDto {
    private Long id;
    private String lastName;
    private String firstName;
    private String surName;
    private LocalDate birthDate;
    private Subject subject;
    private Boolean isPartTime;
    private List<CategoryLevel> categoryLevels;
    private List<LevelOfEducation> educationLevels;
    private List<String> courseNames;
}
