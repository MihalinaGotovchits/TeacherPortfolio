package com.example.teacherportfolio.refresherCourses.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CoursesRequestDto {
    @NotBlank
    private String courseName;

    @Positive
    private int hours;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @Future
    private LocalDate endDate;

    @NotBlank
    private String organization;

    @NotBlank
    private String certificateNumber;

    @NotNull
    private LocalDate certificateDate;

    @NotNull
    private Long teacherId; // Добавлено
}