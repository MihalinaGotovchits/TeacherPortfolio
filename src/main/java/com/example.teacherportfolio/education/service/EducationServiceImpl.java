package com.example.teacherportfolio.education.service;

import com.example.teacherportfolio.education.dto.EducationRequestDto;
import com.example.teacherportfolio.education.dto.EducationResponseDto;
import com.example.teacherportfolio.education.mapper.EducationMapper;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.education.repository.EducationRepository;
import com.example.teacherportfolio.teacher.exception.NotExistForTeacherException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EducationServiceImpl implements EducationService {
    private final EducationRepository educationRepository;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponseDto> getAllEducations() {
        log.info("Получение списка всех образований");
        return educationRepository.findAll().stream()
                .map(EducationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponseDto> getEducationsByTeacherId(Long teacherId) {
        log.info("Получение образований преподавателя с ID: {}", teacherId);
        checkTeacher(teacherId);

        return educationRepository.findByTeacherId(teacherId).stream()
                .map(EducationMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EducationResponseDto> getEducationsByTeacherIdAndLevel(Long teacherId, LevelOfEducation level) {
        log.info("Получение образований уровня {} преподавателя с ID: {}", level, teacherId);
        checkTeacher(teacherId);

        List<Education> educations = educationRepository.findByTeacherIdAndLevelOfEducation(teacherId, level);
        if (educations.isEmpty()) {
            throw new NotFoundException(
                    String.format("Образования уровня %s для преподавателя с ID %s не найдены", level, teacherId));
        }

        return educations.stream()
                .map(EducationMapper::toDto)
                .toList();
    }

    @Override
    public EducationResponseDto getEducationById(Long educationId) {
        Education education = checkEducation(educationId);
        return EducationMapper.toDto(education);
    }

    @Override
    @Transactional
    public EducationResponseDto createEducation(Long teacherId, EducationRequestDto requestDto) {
        log.info("Создание образования для преподавателя с ID: {}", teacherId);
        Teacher teacher = checkTeacher(teacherId);

        Education education = EducationMapper.toEntity(requestDto, teacher);
        Education savedEducation = educationRepository.save(education);

        return EducationMapper.toDto(savedEducation);
    }

    @Override
    @Transactional
    public EducationResponseDto updateEducation(Long teacherId, Long educationId, EducationRequestDto requestDto) {
        log.info("Обновление образования для преподавателя с ID: {}", teacherId);
        Teacher existingTeacher = checkTeacher(teacherId);

        List<Education> educations = existingTeacher.getEducations();
        Education existingEducation = checkEducation(educationId);

        existingEducation.setLevelOfEducation(requestDto.getLevelOfEducation());
        existingEducation.setNameOfEducationalInstitution(requestDto.getNameOfEducationalInstitution());
        existingEducation.setSpeciality(requestDto.getSpeciality());
        existingEducation.setQualification(requestDto.getQualification());
        existingEducation.setEndDate(requestDto.getEndDate());
        existingEducation.setDiplomaNumber(requestDto.getDiplomaNumber());

        Education updatedEducation = educationRepository.save(existingEducation);

        existingTeacher.getEducations().removeIf(edu -> edu.getId().equals(educationId));
        educations.add(updatedEducation);

        return EducationMapper.toDto(updatedEducation);
    }

    @Override
    @Transactional
    public void deleteTeacherEducation(Long teacherId, Long educationId) {
        log.info("Удаление образования с ID {} у преподавателя с ID {}", educationId, teacherId);
        Teacher teacher = checkTeacher(teacherId);
        Education education = checkEducation(educationId);

        if (!education.getTeacher().getId().equals(teacherId)) {
            throw new NotExistForTeacherException("Образование не принадлежит указанному преподавателю");
        }

        teacher.getEducations().remove(education);
        educationRepository.delete(education);
    }

    private Teacher checkTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Преподаватель с ID " + teacherId + " не найден"));
    }

    private Education checkEducation(Long educationId) {
        return educationRepository.findById(educationId)
                .orElseThrow(() -> new NotFoundException("Образование с ID " + educationId + " не найдено"));
    }

}