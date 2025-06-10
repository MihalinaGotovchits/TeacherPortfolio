package com.example.teacherportfolio.teacher.dto;

import com.example.teacherportfolio.teacher.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class TeacherRequestDto {
    private String lastName;
    private String firstName;
    private String surName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private Subject subject;
    private Boolean isPartTime;
}
