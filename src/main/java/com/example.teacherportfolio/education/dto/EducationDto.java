package com.example.teacherportfolio.education.dto;

import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class EducationDto {
    private UUID id;

    private LevelOfEducation levelOfEducation;

    private String nameOfEducationalInstitution;

    private String speciality;

    private String qualification;

    private LocalDate endDate;

    private String diplomaNumber;

    private Teacher teacher;
}
