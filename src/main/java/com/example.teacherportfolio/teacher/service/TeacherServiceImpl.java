package com.example.teacherportfolio.teacher.service;

import com.example.teacherportfolio.teacher.dto.TeacherRequestDto;
import com.example.teacherportfolio.teacher.dto.TeacherShortResponseDto;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.mapper.TeacherMapper;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherShortResponseDto> getAllTeachers() {
        log.info("Получение списка преподавателей");
        return teacherRepository.findAll().stream()
                .map(TeacherMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherShortResponseDto getTeacherById(Long id) {
        log.info("Поиск преподавателя с Id {}", id);
        Teacher teacher = checkTeacher(id);
        return TeacherMapper.toShortDto(teacher);
    }

    @Override
    public List<TeacherShortResponseDto> getTeachersByFirstNameContaining(String firstName) {
        log.info("Поиск преподавателя с фамилией {}", firstName);
        List<Teacher> teacherList = teacherRepository.findByFirstNameContainingIgnoreCase(firstName);

        if (teacherList.isEmpty()) {
            throw new NotFoundException("Преподаватель(и) с фамилией " + firstName + " не найден(ы)");
        }
        return teacherList.stream()
                .map(TeacherMapper::toShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeacherShortResponseDto saveTeacher(TeacherRequestDto teacherRequestDto) {
        log.info("Сохранение нового преподавателя {}", teacherRequestDto);
        Teacher teacher = TeacherMapper.toTeacher(teacherRequestDto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return TeacherMapper.toShortDto(savedTeacher);
    }

    @Override
    public TeacherShortResponseDto update(Long id, TeacherRequestDto teacherRequestDto) {
        log.info("Обновление преподавателя с id {}", id);

        Teacher existingTeacher = checkTeacher(id);

        existingTeacher.setFirstName(teacherRequestDto.getFirstName());
        existingTeacher.setLastName(teacherRequestDto.getLastName());
        existingTeacher.setSurName(teacherRequestDto.getSurName());
        existingTeacher.setBirthDate(teacherRequestDto.getBirthDate());
        existingTeacher.setSubject(teacherRequestDto.getSubject());
        existingTeacher.setIsPartTime(teacherRequestDto.getIsPartTime());

        Teacher updatedTeacher = teacherRepository.save(existingTeacher);
        return TeacherMapper.toShortDto(updatedTeacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        log.info("Удаление преподавателя с Id {}", id);
        Teacher teacher = checkTeacher(id);
        teacherRepository.delete(teacher);
    }

    @Override
    public void deleteAllTeachers() {
        log.info("Удаление всех преподавателей");
        teacherRepository.deleteAll();
    }

    private Teacher checkTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Преподаватель с Id " + teacherId + " не найден")
        );
    }
}