package com.example.teacherportfolio.education.mapper;

import com.example.teacherportfolio.education.dto.EducationDto;
import com.example.teacherportfolio.education.dto.EducationDtoShort;
import com.example.teacherportfolio.education.model.Education;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EducationMapper {
    public static EducationDto toEducationDto(Education education) {
        return EducationDto.builder()
                .id(education.getId())
                .levelOfEducation(education.getLevelOfEducation())
                .speciality(education.getSpeciality())
                .qualification(education.getQualification())
                .nameOfEducationalInstitution(education.getNameOfEducationalInstitution())
                .endDate(education.getEndDate())
                .diplomaNumber(education.getDiplomaNumber())
                .teacher(education.getTeacher())
                .build();
    }

    public static EducationDtoShort toEducationShortDto(Education education) {
        return EducationDtoShort.builder()
                .id(education.getId())
                .levelOfEducation(education.getLevelOfEducation())
                .speciality(education.getSpeciality())
                .qualification(education.getQualification())
                .endDate(education.getEndDate()).build();
    }

    public static Education toEducation(EducationDto educationDto) {
        return Education.builder()
                .id(educationDto.getId())
                .levelOfEducation(educationDto.getLevelOfEducation())
                .speciality(educationDto.getSpeciality())
                .qualification(educationDto.getQualification())
                .nameOfEducationalInstitution(educationDto.getNameOfEducationalInstitution())
                .endDate(educationDto.getEndDate())
                .diplomaNumber(educationDto.getDiplomaNumber())
                .teacher(educationDto.getTeacher())
                .build();
    }
}
