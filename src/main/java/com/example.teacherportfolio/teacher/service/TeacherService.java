package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherRequestDto;
import com.example.teacherportfolio.teacher.dto.TeacherShortResponseDto;

import java.time.LocalDate;
import java.util.List;

public interface TeacherService {
    List<TeacherShortResponseDto> getAllTeachers();

    TeacherShortResponseDto getTeacherById(Long id);

    List<TeacherShortResponseDto> getTeachersByFirstNameContaining(String firstName);

    TeacherShortResponseDto saveTeacher(TeacherRequestDto teacherDtoFull);

    TeacherShortResponseDto update(Long id, TeacherRequestDto teacherDtoFull);

    TeacherShortResponseDto updateTeacherName(Long teacherId, String name);

    TeacherShortResponseDto updateTeacherFirsName(Long teacherId, String firstName);

    TeacherShortResponseDto updateTeacherSurName(Long teacherId, String surName);

    TeacherShortResponseDto updateTeacherBirthday(Long teacherId, LocalDate dateOfBirth);

    TeacherShortResponseDto updateTeacherPartTimeStatus(Long teacherId, Boolean isPartTime);

    void deleteTeacherById(Long id);

    void deleteAllTeachers();
}
