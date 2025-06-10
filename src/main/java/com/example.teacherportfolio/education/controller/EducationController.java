package com.example.teacherportfolio.education.controller;

import com.example.teacherportfolio.education.dto.EducationRequestDto;
import com.example.teacherportfolio.education.dto.EducationResponseDto;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.education.service.EducationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("educations")
public class EducationController {
    private final EducationService educationService;

    @GetMapping
    public List<EducationResponseDto> getAllEducations() {
        log.info("Извлекаем все образования всех преподавателей");
        return educationService.getAllEducations();
    }

    @GetMapping("/teacher/{teacherId}/level/{educationLevel}")
    public List<EducationResponseDto> getEducationsByTeacherIdAndLevel(
            @PathVariable Long teacherId,
            @PathVariable LevelOfEducation educationLevel) {
        log.info("Получаем информацию о {} образовании преподавателя с Id {}", educationLevel, teacherId);
    return educationService.getEducationsByTeacherIdAndLevel(teacherId, educationLevel);
    }

    @GetMapping("/teacher/{teacherId}")
    public List<EducationResponseDto> getEducationsByTeacherId(@PathVariable Long teacherId) {
        log.info("Получаем информацию об образовании преподавателя с Id {}", teacherId);

        return educationService.getEducationsByTeacherId(teacherId);
    }

    @GetMapping("/{educationId}")
    public EducationResponseDto getEducationById( @PathVariable Long educationId) {
        return educationService.getEducationById(educationId);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EducationResponseDto saveEducationByTeacherId(
            @PathVariable Long teacherId,
            @RequestBody EducationRequestDto educationDto) {
        log.info("Сохраняем информацию об образовании преподавателя с Id {}", teacherId);
        return educationService.createEducation(teacherId, educationDto);
    }

    @PatchMapping("{educationId}/teacher/{teacherId}")
    public EducationResponseDto updateTeacherEducation(
            @PathVariable Long teacherId,
            @PathVariable Long educationId,
            @RequestBody EducationRequestDto educationDto) {
        log.info("Обновляем информацию об образовании преподавателя с Id {}", teacherId);
        return educationService.updateEducation(teacherId, educationId, educationDto);
    }

    @DeleteMapping("/{educationId}/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEducationByTeacherId(
            @PathVariable Long teacherId,
            @PathVariable Long educationId) {
        log.info("Удаляем образование у преподавателя с Id {}", teacherId);
        educationService.deleteTeacherEducation(teacherId, educationId);
    }
}
