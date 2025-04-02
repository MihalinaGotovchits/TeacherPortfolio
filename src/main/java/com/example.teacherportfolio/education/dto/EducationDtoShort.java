package com.example.teacherportfolio.education.dto;

import com.example.teacherportfolio.education.model.LevelOfEducation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class EducationDtoShort {
    private UUID id;

    private LevelOfEducation levelOfEducation;

    private String speciality;

    private String qualification;

    private LocalDate endDate;
}
