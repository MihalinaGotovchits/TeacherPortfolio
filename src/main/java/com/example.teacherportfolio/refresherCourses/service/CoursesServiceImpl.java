package com.example.teacherportfolio.refresherCourses.service;

import com.example.teacherportfolio.refresherCourses.dto.CoursesDto;
import com.example.teacherportfolio.refresherCourses.mapper.CoursesMapper;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.refresherCourses.repository.CoursesRepository;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.exception.NotExistForTeacherException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.mapper.TeacherMapper;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoursesServiceImpl implements CoursesService {
    private final CoursesRepository coursesRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<CoursesDto> getAllCourses() {
        return coursesRepository.findAll().stream().map(CoursesMapper::toRefreshCoursesDto).toList();
    }

    @Override
    public CoursesDto getCourseById(UUID courseId) {
        Course courses = coursesRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Курс с Id " + courseId + " не найден")
        );
        return CoursesMapper.toRefreshCoursesDto(courses);
    }

    @Override
    public List<CoursesDto> getCoursesByTeacherId(UUID teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Получение всех курсов преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        return teacherRepository.findCoursesByTeacherId(teacherId).stream()
                .map(CoursesMapper::toRefreshCoursesDto).collect(Collectors.toList());
    }

    @Override
    public List<TeacherDtoFull> getTeachersByCourseId(UUID courseId) {
        Course refresherCourses = checkCourse(courseId);
        log.info("Получение списка преподавателей с курса {}", refresherCourses.getNameOfCourse());
        return coursesRepository.findTeachersByCourseId(courseId).stream()
                .map(TeacherMapper::toTeacherDtoFull).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CoursesDto saveCourseByTeacherId(UUID teacherId, CoursesDto refreshCoursesDto) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Сохранение курса '{}' для преподавателя {} {} {}",
                refreshCoursesDto.getNameOfCourse(),
                teacher.getFirstName(),
                teacher.getName(),
                teacher.getSurName());

        Course course = CoursesMapper.toRefreshCourses(refreshCoursesDto);
        course = coursesRepository.save(course);

        teacher.getCourses().add(course);

        return CoursesMapper.toRefreshCoursesDto(course);
    }

    @Override
    public CoursesDto updateCoursesByTeacherId(UUID teacherId, CoursesDto refreshCoursesDto) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Обновление курса {} у преподавателя {}{}{}", refreshCoursesDto.getNameOfCourse(),
                teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        Course existCourse = checkCourse(refreshCoursesDto.getId());

        if (!teacher.getCourses().contains(existCourse)) {
            throw new NotExistForTeacherException(String.format("Курс %s не пренадлежит данному преподавателю",
                    refreshCoursesDto.getNameOfCourse()));
        }

        existCourse.setNameOfCourse(refreshCoursesDto.getNameOfCourse());
        existCourse.setCountOfHours(refreshCoursesDto.getCountOfHours());
        existCourse.setNameOfEducationalInstitution(refreshCoursesDto.getNameOfEducationalInstitution());
        existCourse.setStartOfTraining(refreshCoursesDto.getStartOfTraining());
        existCourse.setEndOfTraining(refreshCoursesDto.getEndOfTraining());
        existCourse.setNumberOfCertificate(refreshCoursesDto.getNumberOfCertificate());
        existCourse.setDateOfCertificate(refreshCoursesDto.getDateOfCertificate());

        validateCourseDates(existCourse);

        return CoursesMapper.toRefreshCoursesDto(coursesRepository.save(existCourse));
    }

    @Override
    @Transactional
    public CoursesDto updateTeacherCourseByName(UUID teacherId, String nameOfCourse, CoursesDto updateDto) {
        Teacher teacher = checkTeacher(teacherId);

        Course existingCourse = coursesRepository.findByTeacherIdAndCourseName(teacherId, nameOfCourse)
                .orElseThrow(() -> new NotFoundException(
                        "Курс с названием '" + nameOfCourse + "' не найден"));

        log.info("Обновление курса {} у преподавателя {}{}{}", existingCourse.getNameOfCourse(),
                teacher.getFirstName(), teacher.getName(), teacher.getSurName());

        existingCourse.setNameOfCourse(updateDto.getNameOfCourse());
        existingCourse.setCountOfHours(updateDto.getCountOfHours());
        existingCourse.setNameOfEducationalInstitution(updateDto.getNameOfEducationalInstitution());
        existingCourse.setStartOfTraining(updateDto.getStartOfTraining());
        existingCourse.setEndOfTraining(updateDto.getEndOfTraining());
        existingCourse.setNumberOfCertificate(updateDto.getNumberOfCertificate());
        existingCourse.setDateOfCertificate(updateDto.getDateOfCertificate());

        validateCourseDates(existingCourse);

        return CoursesMapper.toRefreshCoursesDto(coursesRepository.save(existingCourse));
    }

    @Override
    @Transactional
    public void deleteCourseByTeacherId(UUID teacherId, UUID courseId) {
        Teacher teacher = checkTeacher(teacherId);
        Course refresherCourses = checkCourse(courseId);

        if (!teacher.getCourses().contains(refresherCourses)) {
            throw new NotExistForTeacherException("Курс " + refresherCourses.getNameOfCourse()
                    + " не принадлежит указанному преподавателю");
        }
        log.info("Удаление курса {} у преподавателя {}{}{}", refresherCourses.getNameOfCourse(),
                teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        teacher.getCourses().remove(refresherCourses);
        teacherRepository.save(teacher);
        coursesRepository.delete(refresherCourses);
    }

    @Override
    @Transactional
    public void deleteAllCoursesByTeacherId(UUID teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Удаление всех курсов у преподавателя {}{}{}",
                teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        List<UUID> coursesIds = teacher.getCourses().stream()
                .map(Course::getId).collect(Collectors.toList());
        teacher.getCourses().clear();
        teacherRepository.save(teacher);

        coursesRepository.deleteAllById(coursesIds);
    }

    private Teacher checkTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Преподаватель с Id " + teacherId + " не найден")
        );
    }

    private Course checkCourse(UUID courseId) {
        return coursesRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Курс с Id " + courseId + " не найден")
        );
    }

    private void validateCourseDates(Course course) {
        if (course.getEndOfTraining().isBefore(course.getStartOfTraining())) {
            throw new IllegalStateException("Дата окончания курса не может быть раньше даты начала");
        }
    }
}
