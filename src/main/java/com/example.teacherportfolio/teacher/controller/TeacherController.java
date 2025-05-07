package com.example.teacherportfolio.teacher.controller;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teachers")
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDtoFull> getAllTeachers() {
        log.info("Получение списка препподавателей");
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDtoFull getTeacherById(@PathVariable UUID id) {
        log.info("Поиск преподавателя с Id {}", id);
        return teacherService.getTeacherById(id);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDtoFull saveTeacher(@Valid @RequestBody TeacherDtoFull teacherDtoFull) {
        log.info("Сохранение нового преподавателя {}", teacherDtoFull);
        return teacherService.saveTeacher(teacherDtoFull);
    }

    @PatchMapping("/{id}")
    public TeacherDtoFull update(
            @PathVariable UUID id,
            @Valid @RequestBody TeacherDtoFull teacherDtoFull) {
        log.info("Обновление преподавателя {}{}{}",teacherDtoFull.getFirstName(), teacherDtoFull.getName(), teacherDtoFull.getSurName());
        return teacherService.update(id, teacherDtoFull);
    }

    @PatchMapping("/{id}/name")
    public TeacherDtoFull updateTeacherName(@PathVariable UUID id,
                                            @RequestParam String name) {
        log.info("Обновление имени у преподавателя {}", id);
        return teacherService.updateTeacherName(id, name);
    }

    @PatchMapping("/{id}/firstname")
    public TeacherDtoFull updateTeacherFirstName(@PathVariable UUID id,
                                                 @RequestParam String firstName) {
        log.info("Обновление фамилии у преподавателя {}", id);
        return teacherService.updateTeacherFirsName(id, firstName);
    }


    @PatchMapping("/{id}/surname")
    public TeacherDtoFull updateTeacherSurName(@PathVariable UUID id,
                                               @RequestParam String surName) {
        log.info("Обновление фамилии у преподавателя {}", id);
        return teacherService.updateTeacherSurName(id, surName);
    }


    @PatchMapping("/{id}/birthday")
    public TeacherDtoFull updateTeacherDateOfBirth(@PathVariable UUID id,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateOfBirth) {
        log.info("Обновление даты рождения у преподавателя {}", id);
        return teacherService.updateTeacherBirthday(id, dateOfBirth);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeacherById(@PathVariable UUID id) {
        log.info("Удаление преподавателя с Id {}", id);
        teacherService.deleteTeacherById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTeachers() {
        log.info("Удаление всех преподавателей");
        teacherService.deleteAllTeachers();
    }
}
