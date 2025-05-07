package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TeacherService {
    List<TeacherDtoFull> getAllTeachers();

    TeacherDtoFull getTeacherById(UUID id);

    TeacherDtoFull saveTeacher(TeacherDtoFull teacherDtoFull);

    TeacherDtoFull update(UUID id, TeacherDtoFull teacherDtoFull);

    TeacherDtoFull updateTeacherName(UUID teacherId, String name);

    TeacherDtoFull updateTeacherFirsName(UUID teacherId, String firstName);

    TeacherDtoFull updateTeacherSurName(UUID teacherId, String surName);

    TeacherDtoFull updateTeacherBirthday(UUID teacherId, LocalDate dateOfBirth);

    void deleteTeacherById(UUID id);

    void deleteAllTeachers();
}
