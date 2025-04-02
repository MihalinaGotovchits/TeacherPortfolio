package com.example.teacherportfolio.teacher.controller;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public TeacherDtoFull saveTeacher(@RequestBody TeacherDtoFull teacherDtoFull) {
        log.info("Сохранение нового преподавателя {}", teacherDtoFull);
        return teacherService.saveTeacher(teacherDtoFull);
    }

    @PutMapping("/{id}")
    public TeacherDtoFull update(
            @PathVariable UUID id,
            @RequestBody TeacherDtoFull teacherDtoFull) {
        log.info("Обновление преподавателя {}{}{}",teacherDtoFull.getFirstName(), teacherDtoFull.getName(), teacherDtoFull.getSurName());
        return teacherService.update(id, teacherDtoFull);
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
