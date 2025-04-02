package com.example.teacherportfolio.education.service;

import com.example.teacherportfolio.education.dto.EducationDto;
import com.example.teacherportfolio.education.model.LevelOfEducation;

import java.util.List;
import java.util.UUID;

public interface EducationService {
    List<EducationDto> getAllEducations();

    List<EducationDto> getEducationsByTeacherIdAndLevel(UUID teacherId, LevelOfEducation educationLevel);

    List<EducationDto> getEducationsByTeacherId(UUID teacherId);

    EducationDto saveEducationByTeacherId(UUID teacherId, EducationDto educationDto);

    EducationDto updateTeacherEducation(UUID teacherId, EducationDto educationDto);

    void deleteEducationByTeacherId(UUID teacherId, UUID educationId);
}
