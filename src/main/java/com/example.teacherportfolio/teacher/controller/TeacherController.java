package com.example.teacherportfolio.teacher.controller;

import com.example.teacherportfolio.teacher.dto.TeacherRequestDto;
import com.example.teacherportfolio.teacher.dto.TeacherShortResponseDto;
import com.example.teacherportfolio.teacher.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("teachers")
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping
    @Transactional
    public List<TeacherShortResponseDto> getAllTeachers() {
        log.info("Получение списка препподавателей");
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    @Transactional
    public TeacherShortResponseDto getTeacherById(@PathVariable Long id) {
        log.info("Поиск преподавателя с Id {}", id);
        return teacherService.getTeacherById(id);
    }

    @GetMapping("/search/firstName")
    @Transactional
    public List<TeacherShortResponseDto> getTeachersByFirstNameContaining(@RequestParam String firstName) {
        log.info("Запрос преподавателей с фамилией " + firstName);
        return teacherService.getTeachersByFirstNameContaining(firstName);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public TeacherShortResponseDto saveTeacher(@Valid @RequestBody TeacherRequestDto teacherDtoFull) {
        log.info("Сохранение нового преподавателя {}", teacherDtoFull);
        return teacherService.saveTeacher(teacherDtoFull);
    }

    @PatchMapping("/{id}")
    @Transactional
    public TeacherShortResponseDto update(
            @PathVariable Long id,
            @Valid @RequestBody TeacherRequestDto teacherDtoFull) {
        log.info("Обновление преподавателя {}{}{}",teacherDtoFull.getLastName(), teacherDtoFull.getFirstName(), teacherDtoFull.getSurName());
        return teacherService.update(id, teacherDtoFull);
    }

    @PatchMapping("/{id}/name")
    @Transactional
    public TeacherShortResponseDto updateTeacherName(@PathVariable Long id,
                                               @RequestParam String name) {
        log.info("Обновление имени у преподавателя {}", id);
        return teacherService.updateTeacherName(id, name);
    }

    @PatchMapping("/{id}/firstname")
    @Transactional
    public TeacherShortResponseDto updateTeacherFirstName(@PathVariable Long id,
                                                    @RequestParam String firstName) {
        log.info("Обновление фамилии у преподавателя {}", id);
        return teacherService.updateTeacherFirsName(id, firstName);
    }


    @PatchMapping("/{id}/surname")
    @Transactional
    public TeacherShortResponseDto updateTeacherSurName(@PathVariable Long id,
                                                  @RequestParam String surName) {
        log.info("Обновление отчества у преподавателя {}", id);
        return teacherService.updateTeacherSurName(id, surName);
    }


    @PatchMapping("/{id}/birthday")
    @Transactional
    public TeacherShortResponseDto updateTeacherDateOfBirth(@PathVariable Long id,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate dateOfBirth) {
        log.info("Обновление даты рождения у преподавателя {}", id);
        return teacherService.updateTeacherBirthday(id, dateOfBirth);
    }

    @PatchMapping("/{id}/partTime")
    @Transactional
    public TeacherShortResponseDto updateTeacherPartTimeStatus(
            @PathVariable Long id,
            @RequestParam Boolean isPartTime
    ) {
        log.info("Обновление статуса совместителя для преподавателя {}: {}", id, isPartTime);
        return teacherService.updateTeacherPartTimeStatus(id, isPartTime);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteTeacherById(@PathVariable Long id) {
        log.info("Удаление преподавателя с Id {}", id);
        teacherService.deleteTeacherById(id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void deleteAllTeachers() {
        log.info("Удаление всех преподавателей");
        teacherService.deleteAllTeachers();
    }
}
