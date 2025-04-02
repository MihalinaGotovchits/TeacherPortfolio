package com.example.teacherportfolio.education.service;

import com.example.teacherportfolio.education.dto.EducationDto;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EducationServiceImpl implements EducationService  {
    private final EducationRepository educationRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<EducationDto> getAllEducations() {
        log.info("Извлекаем все образования всех преподавателей");
        return educationRepository.findAll().stream()
                .map(EducationMapper::toEducationDto).toList();
    }

    @Override
    public List<EducationDto> getEducationsByTeacherIdAndLevel(UUID teacherId, LevelOfEducation educationLevel) {
        Teacher teacher = checkTeacher(teacherId);

        log.info("Получаем информацию о {} образовании преподавателя {}{}{}", educationLevel, teacher.getFirstName(),
                teacher.getName(), teacher.getSurName());

        List<Education> educations = educationRepository
                .findByTeacherIdAndLevelOfEducation(teacherId, educationLevel);

        if (educations.isEmpty()) {
            throw new NotFoundException(
                    String.format("Образования уровня %s для преподавателя с ID %s не найдены",
                            educationLevel, teacherId));
        }

        return educations.stream()
                .map(EducationMapper::toEducationDto)
                .toList();
    }

    @Override
    public List<EducationDto> getEducationsByTeacherId(UUID teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Получаем информацию об образовании преподавателя {}{}{}", teacher.getFirstName(),
                teacher.getName(), teacher.getSurName());

        return educationRepository.findByTeacherId(teacherId).stream()
                .map(EducationMapper::toEducationDto).toList();
    }

    @Override
    public EducationDto saveEducationByTeacherId(UUID teacherId, EducationDto educationDto) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Сохраняем информацию об образовании преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(),
                teacher.getSurName());
        Education education = EducationMapper.toEducation(educationDto);
        education.setTeacher(teacher);
        Education savedEducation = educationRepository.save(education);
        return EducationMapper.toEducationDto(savedEducation);
    }

    @Override
    public EducationDto updateTeacherEducation(UUID teacherId, EducationDto educationDto) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновляем информацию об образовании преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(),
                teacher.getSurName());
        Education existingEducation = checkEducation(educationDto.getId());
        if (!existingEducation.getTeacher().getId().equals(teacherId)) {
            throw new NotExistForTeacherException("Образование не принадлежит указанному преподавателю");
        }

        existingEducation.setLevelOfEducation(educationDto.getLevelOfEducation());
        existingEducation.setNameOfEducationalInstitution(educationDto.getNameOfEducationalInstitution());
        existingEducation.setSpeciality(educationDto.getSpeciality());
        existingEducation.setQualification(educationDto.getQualification());
        existingEducation.setEndDate(educationDto.getEndDate());
        existingEducation.setDiplomaNumber(educationDto.getDiplomaNumber());

        Education updateEducation = educationRepository.save(existingEducation);
        return EducationMapper.toEducationDto(updateEducation);
    }

    @Override
    public void deleteEducationByTeacherId(UUID teacherId, UUID educationId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Удаляем образование у преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        Education education = checkEducation(educationId);
        if (!education.getTeacher().getId().equals(teacherId)) {
            throw new NotExistForTeacherException("Образование не принадлежит указанному преподавателю");
        }
        educationRepository.delete(education);
    }

    private Teacher checkTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Преподаватель с Id " + teacherId +" не найден")
        );
    }

    private Education checkEducation(UUID educationId) {
        return educationRepository.findById(educationId).orElseThrow(
                () -> new NotFoundException("Образование не найдено")
        );
    }
}
