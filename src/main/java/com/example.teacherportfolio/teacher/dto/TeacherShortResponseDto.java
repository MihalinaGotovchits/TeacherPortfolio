package com.example.teacherportfolio.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TeacherShortResponseDto {
    private String lastName;
    private String firstName;
    private String surName;
}
