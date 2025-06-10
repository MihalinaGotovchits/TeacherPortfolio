package com.example.teacherportfolio.refresherCourses.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class CoursesResponseDto {
    private Long id;
    private String courseName;
    private int hours;
    private LocalDate startDate;
    private LocalDate endDate;
    private String organization;
    private String certificateNumber;
    private LocalDate certificateDate;
    private Long teacherId;
}
