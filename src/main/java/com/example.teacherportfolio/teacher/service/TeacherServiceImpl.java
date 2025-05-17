package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.mapper.TeacherMapper;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherDtoFull> getAllTeachers() {
        log.info("Получение списка препподавателей");
        return teacherRepository.findAll().stream().map(TeacherMapper::toTeacherDtoFull).collect(Collectors.toList());
    }

    @Override
    public TeacherDtoFull getTeacherById(UUID id) {
        log.info("Поиск преподавателя с Id {}", id);
        Teacher teacher = checkTeacher(id);
        return TeacherMapper.toTeacherDtoFull(teacher);
    }

    @Override
    public List<TeacherDtoFull> getTeachersByFirstNameContaining(String firstName) {
        log.info("Поиск преподавателя с фамилией {}",firstName);
        List<Teacher> teacherList = teacherRepository.findByFirstNameContainingIgnoreCase(firstName);

        if (teacherList.isEmpty()){
            throw new NotFoundException("Преподаватель(и) с фамилией " + firstName + " не найден(ы)");
        }
        return teacherList.stream()
                .map(TeacherMapper::toTeacherDtoFull)
                .collect(Collectors.toList());
    }


    @Override
    public TeacherDtoFull saveTeacher(TeacherDtoFull teacherDtoFull) {
        log.info("Сохранение нового преподавателя {}", teacherDtoFull);
        Teacher teacher = teacherRepository.save(TeacherMapper.toTeacher(teacherDtoFull));
        return TeacherMapper.toTeacherDtoFull(teacher);
    }

    @Override
    public TeacherDtoFull update(UUID id, TeacherDtoFull teacherDtoFull) {
        log.info("Обновление преподавателя {} {} {}",teacherDtoFull.getFirstName(), teacherDtoFull.getName(), teacherDtoFull.getSurName());

        Teacher teacher = checkTeacher(id);

        teacher.setId(teacherDtoFull.getId());
        teacher.setFirstName(teacherDtoFull.getFirstName());
        teacher.setName(teacherDtoFull.getName());
        teacher.setSurName(teacherDtoFull.getSurName());
        teacher.setDateOfBirth(teacherDtoFull.getDateOfBirth());
        teacher.setSubject(teacherDtoFull.getSubject());
        teacher.setCategory(teacherDtoFull.getCategory());
        teacher.setEducation(teacherDtoFull.getEducation());
        teacher.setWorkExperience(teacherDtoFull.getWorkExperience());
        teacher.setCourses(teacherDtoFull.getRefresherCoursesList());

        return TeacherMapper.toTeacherDtoFull(teacher);
    }

    @Override
    public TeacherDtoFull updateTeacherName(UUID teacherId, String name) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление имени у преподавателя {} {} {}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        teacher.setName(name);
        return TeacherMapper.toTeacherDtoFull(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDtoFull updateTeacherFirsName(UUID teacherId, String firstName) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление фамилии у преподавателя {} {} {}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        teacher.setFirstName(firstName);
        return TeacherMapper.toTeacherDtoFull(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDtoFull updateTeacherSurName(UUID teacherId, String surName) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление отчества у преподавателя {} {} {}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        teacher.setSurName(surName);
        return TeacherMapper.toTeacherDtoFull(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDtoFull updateTeacherBirthday(UUID teacherId, LocalDate dateOfBirth) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление дня рождения у преподавателя {} {} {}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        teacher.setDateOfBirth(dateOfBirth);
        return TeacherMapper.toTeacherDtoFull(teacherRepository.save(teacher));
    }

    @Override
    public TeacherDtoFull updateTeacherPartTimeStatus(UUID teacherId, Boolean isPartTime) {
        Teacher teacher = checkTeacher(teacherId);
        switch (isPartTime.toString()) {
            case "true":
            log.info("Преподаватель {} является совместителем", teacher);
            case "false":
            log.info("Преподаватель {} не является совместителем", teacher);
        }
        teacher.setIsPartTime(isPartTime);
        return TeacherMapper.toTeacherDtoFull(teacherRepository.save(teacher));
    }

    @Override
    public void deleteTeacherById(UUID id) {
        log.info("Удаление преподавателя с Id {}", id);
        Teacher teacher = checkTeacher(id);
        teacherRepository.delete(teacher);
    }

    @Override
    public void deleteAllTeachers() {
        log.info("Удаление всех преподавателей");
        teacherRepository.deleteAll();
    }

    private Teacher checkTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Преподаватель с Id " + teacherId + " не найден")
        );
    }
}
