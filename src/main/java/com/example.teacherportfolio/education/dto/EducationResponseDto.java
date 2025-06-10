package com.example.teacherportfolio.education.dto;

import com.example.teacherportfolio.education.model.LevelOfEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponseDto {
    private Long id;
    private LevelOfEducation levelOfEducation;
    private String nameOfEducationalInstitution;
    private String speciality;
    private String qualification;
    private LocalDate endDate;
    private String diplomaNumber;
    private Long teacherId;
    private String teacherFullName;
}