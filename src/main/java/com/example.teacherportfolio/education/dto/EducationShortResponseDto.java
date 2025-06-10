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
public class EducationShortResponseDto {
    private Long id;
    private LevelOfEducation levelOfEducation;
    private String speciality;
    private String qualification;
    private LocalDate endDate;
}