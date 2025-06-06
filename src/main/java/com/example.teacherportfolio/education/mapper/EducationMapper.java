package com.example.teacherportfolio.education.mapper;

import com.example.teacherportfolio.education.dto.EducationRequestDto;
import com.example.teacherportfolio.education.dto.EducationResponseDto;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EducationMapper {
    public static Education toEntity(EducationRequestDto dto, Teacher teacher) {
        return Education.builder()
                .levelOfEducation(dto.getLevelOfEducation())
                .nameOfEducationalInstitution(dto.getNameOfEducationalInstitution())
                .speciality(dto.getSpeciality())
                .qualification(dto.getQualification())
                .endDate(dto.getEndDate())
                .diplomaNumber(dto.getDiplomaNumber())
                .teacher(teacher)
                .build();
    }

    public static EducationResponseDto toDto(Education education) {
        return EducationResponseDto.builder()
                .id(education.getId())
                .levelOfEducation(education.getLevelOfEducation())
                .nameOfEducationalInstitution(education.getNameOfEducationalInstitution())
                .speciality(education.getSpeciality())
                .qualification(education.getQualification())
                .endDate(education.getEndDate())
                .diplomaNumber(education.getDiplomaNumber())
                .teacherId(education.getTeacher().getId())
                .teacherFullName(getTeacherFullName(education.getTeacher()))
                .build();
    }

    private static String getTeacherFullName(Teacher teacher) {
        return String.format("%s %s %s",
                teacher.getLastName(),
                teacher.getFirstName(),
                teacher.getSurName()).trim();
    }
}