package com.example.teacherportfolio.education.service;

import com.example.teacherportfolio.education.dto.EducationRequestDto;
import com.example.teacherportfolio.education.dto.EducationResponseDto;
import com.example.teacherportfolio.education.model.LevelOfEducation;

import java.util.List;

public interface EducationService {
    List<EducationResponseDto> getAllEducations();
    List<EducationResponseDto> getEducationsByTeacherId(Long teacherId);
    EducationResponseDto getEducationById(Long educationId);
    List<EducationResponseDto> getEducationsByTeacherIdAndLevel(Long teacherId, LevelOfEducation level);
    EducationResponseDto createEducation(Long teacherId, EducationRequestDto requestDto);
    EducationResponseDto updateEducation(Long teacherId, Long educationId, EducationRequestDto requestDto);
    void deleteTeacherEducation(Long teacherId, Long educationId);
}