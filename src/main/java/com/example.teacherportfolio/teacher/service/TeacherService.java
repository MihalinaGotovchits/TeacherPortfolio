package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;

import java.util.List;
import java.util.UUID;

public interface TeacherService {
    List<TeacherDtoFull> getAllTeachers();

    TeacherDtoFull getTeacherById(UUID id);

    TeacherDtoFull saveTeacher(TeacherDtoFull teacherDtoFull);

    TeacherDtoFull update(UUID id, TeacherDtoFull teacherDtoFull);

    void deleteTeacherById(UUID id);

    void deleteAllTeachers();
}
