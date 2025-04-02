package com.example.teacherportfolio.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class TeacherDtoShort {
    private UUID id;

    private String firstName;

    private String name;

    private String surName;
}
