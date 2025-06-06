package com.example.teacherportfolio.education.dto;

import com.example.teacherportfolio.education.model.LevelOfEducation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationRequestDto {
    @NotNull(message = "Уровень образования обязателен")
    private LevelOfEducation levelOfEducation;

    @NotBlank(message = "Название учебного заведения обязательно")
    private String nameOfEducationalInstitution;

    @NotBlank(message = "Специальность обязательна")
    private String speciality;

    @NotBlank(message = "Квалификация обязательна")
    private String qualification;

    @NotNull(message = "Дата окончания обязательна")
    private LocalDate endDate;

    @NotBlank(message = "Номер диплома обязателен")
    private String diplomaNumber;

    @NotNull(message = "ID преподавателя обязательно")
    private Long teacherId;
}