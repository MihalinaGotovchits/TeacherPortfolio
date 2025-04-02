package com.example.teacherportfolio.education.controller;

import com.example.teacherportfolio.education.dto.EducationDto;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.education.service.EducationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/education")
public class EducationController {
    private final EducationService educationService;

    @GetMapping
    public List<EducationDto> getAllEducations() {
        log.info("Извлекаем все образования всех преподавателей");
        return educationService.getAllEducations();
    }

    @GetMapping("/teacher/{teacherId}/level/{educationLevel}")
    public List<EducationDto> getEducationsByTeacherIdAndLevel(
            @PathVariable UUID teacherId,
            @PathVariable LevelOfEducation educationLevel) {
        log.info("Получаем информацию о {} образовании преподавателя с Id {}", educationLevel, teacherId);
    return educationService.getEducationsByTeacherIdAndLevel(teacherId, educationLevel);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<EducationDto> getEducationsByTeacherId(@PathVariable UUID teacherId) {
        log.info("Получаем информацию об образовании преподавателя с Id {}", teacherId);

        return educationService.getEducationsByTeacherId(teacherId);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EducationDto saveEducationByTeacherId(
            @PathVariable UUID teacherId,
            @RequestBody EducationDto educationDto) {
        log.info("Сохраняем информацию об образовании преподавателя с Id {}", teacherId);
        return educationService.saveEducationByTeacherId(teacherId, educationDto);
    }

    @PutMapping("/teacher/{teacherId}")
    public EducationDto updateTeacherEducation(
            @PathVariable UUID teacherId,
            @RequestBody EducationDto educationDto) {
        log.info("Обновляем информацию об образовании преподавателя с Id {}", teacherId);
        return educationService.updateTeacherEducation(teacherId, educationDto);
    }

    @DeleteMapping("/teacher/{teacherId}/{educationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEducationByTeacherId(
            @PathVariable UUID teacherId, UUID educationId) {
        log.info("Удаляем образование у преподавателя с Id {}", teacherId);
        educationService.deleteEducationByTeacherId(teacherId, educationId);
    }
}
