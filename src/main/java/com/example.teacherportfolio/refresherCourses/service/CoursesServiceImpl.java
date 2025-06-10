package com.example.teacherportfolio.refresherCourses.service;

import com.example.teacherportfolio.refresherCourses.dto.CoursesRequestDto;
import com.example.teacherportfolio.refresherCourses.dto.CoursesResponseDto;
import com.example.teacherportfolio.refresherCourses.mapper.CoursesMapper;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.refresherCourses.repository.CoursesRepository;
import com.example.teacherportfolio.teacher.exception.NotExistForTeacherException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CoursesServiceImpl implements CoursesService {
    private final CoursesRepository coursesRepository;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CoursesResponseDto> getAllCourses() {
        return coursesRepository.findAll().stream().map(CoursesMapper::toDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CoursesResponseDto getCourseById(Long courseId) {
        Course courses = coursesRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Курс с Id " + courseId + " не найден")
        );
        return CoursesMapper.toDto(courses);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CoursesResponseDto> getCoursesByTeacherId(Long teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Получение всех курсов преподавателя {}{}{}", teacher.getLastName(), teacher.getFirstName(),
                teacher.getSurName());
        return teacherRepository.findCoursesByTeacherId(teacherId).stream()
                .map(CoursesMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CoursesResponseDto saveCourseByTeacherId(Long teacherId, CoursesRequestDto coursesRequestDto) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Сохранение курса '{}' для преподавателя {} {} {}",
                coursesRequestDto.getCourseName(),
                teacher.getLastName(),
                teacher.getFirstName(),
                teacher.getSurName());

        Course course = CoursesMapper.toEntity(coursesRequestDto, teacher);
        course = coursesRepository.save(course);

        teacher.getCourses().add(course);

        return CoursesMapper.toDto(course);
    }

    @Override
    @Transactional
    public CoursesResponseDto updateCoursesByTeacherId(Long teacherId, Course course) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление курса {} у преподавателя {}{}{}", course.getCourseName(),
                teacher.getLastName(), teacher.getFirstName(), teacher.getSurName());
        Course existCourse = checkCourse(course.getId());

        if (!teacher.getCourses().contains(existCourse)) {
            throw new NotExistForTeacherException(String.format("Курс %s не пренадлежит данному преподавателю",
                    course.getCourseName()));
        }

        existCourse.setCourseName(course.getCourseName());
        existCourse.setCountOfHours(course.getCountOfHours());
        existCourse.setOrganization(course.getOrganization());
        existCourse.setStartDate(course.getStartDate());
        existCourse.setEndDate(course.getEndDate());
        existCourse.setCertificateNumber(course.getCertificateNumber());
        existCourse.setCertificateNumber(course.getCertificateNumber());

        validateCourseDates(existCourse);

        return CoursesMapper.toDto(coursesRepository.save(existCourse));
    }

    @Override
    @Transactional
    public void deleteCourseByTeacherId(Long teacherId, Long courseId) {
        Teacher teacher = checkTeacher(teacherId);
        Course refresherCourses = checkCourse(courseId);

        if (!teacher.getCourses().contains(refresherCourses)) {
            throw new NotExistForTeacherException("Курс " + refresherCourses.getCourseName()
                    + " не принадлежит указанному преподавателю");
        }
        log.info("Удаление курса {} у преподавателя {}{}{}", refresherCourses.getCourseName(),
                teacher.getLastName(), teacher.getFirstName(), teacher.getSurName());
        teacher.getCourses().remove(refresherCourses);
        teacherRepository.save(teacher);
        coursesRepository.delete(refresherCourses);
    }

    @Override
    @Transactional
    public void deleteAllCoursesByTeacherId(Long teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Удаление всех курсов у преподавателя {}{}{}",
                teacher.getLastName(), teacher.getFirstName(), teacher.getSurName());
        List<Long> coursesIds = teacher.getCourses().stream()
                .map(Course::getId).collect(Collectors.toList());
        teacher.getCourses().clear();
        teacherRepository.save(teacher);

        coursesRepository.deleteAllById(coursesIds);
    }

    private Teacher checkTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Преподаватель с Id " + teacherId + " не найден")
        );
    }

    private Course checkCourse(Long courseId) {
        return coursesRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Курс с Id " + courseId + " не найден")
        );
    }

    private void validateCourseDates(Course course) {
        if (course.getEndDate().isBefore(course.getStartDate())) {
            throw new IllegalStateException("Дата окончания курса не может быть раньше даты начала");
        }
    }
}