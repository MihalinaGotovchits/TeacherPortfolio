package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherRequestDto;
import com.example.teacherportfolio.teacher.dto.TeacherShortResponseDto;

import java.util.List;

public interface TeacherService {
    List<TeacherShortResponseDto> getAllTeachers();

    TeacherShortResponseDto getTeacherById(Long id);

    List<TeacherShortResponseDto> getTeachersByFirstNameContaining(String firstName);

    TeacherShortResponseDto saveTeacher(TeacherRequestDto teacherDtoFull);

    TeacherShortResponseDto update(Long id, TeacherRequestDto teacherDtoFull);

    void deleteTeacherById(Long id);

    void deleteAllTeachers();
}
