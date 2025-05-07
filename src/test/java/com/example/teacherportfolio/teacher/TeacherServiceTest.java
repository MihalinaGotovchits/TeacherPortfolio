package com.example.teacherportfolio.teacher;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.exception.IllegalArgumentException;
import com.example.teacherportfolio.teacher.mapper.TeacherMapper;
import com.example.teacherportfolio.teacher.model.Subject;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import com.example.teacherportfolio.teacher.service.TeacherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private TeacherServiceImpl teacherService;

    private final UUID teacherId = UUID.randomUUID();
    private final UUID randomUUIDForCategory = UUID.randomUUID();
    private final Category category = new Category(randomUUIDForCategory,
            CategoryLevel.FIRST,
            "Распоряжение",
            "A-29",
            LocalDate.of(2025,4,2),
            new ArrayList<>());
    private final TeacherDtoFull teacherDtoFull = TeacherDtoFull.builder()
            .id(teacherId)
            .firstName("FirstName")
            .name("Name")
            .surName("SurName")
            .dateOfBirth(LocalDate.of(1995,1,12))
            .subject(Subject.STRING)
            .category(category)
            .education(new ArrayList<>())
            .refresherCoursesList(new ArrayList<>())
            .workExperience(7)
            .build();

    private final Teacher teacher = Teacher.builder()
            .id(teacherId)
            .firstName("FirstName")
            .name("Name")
            .surName("SurName")
            .dateOfBirth(LocalDate.of(1995,1,12))
            .subject(Subject.STRING)
            .category(category)
            .education(new ArrayList<>())
            .courses(new ArrayList<>())
            .workExperience(7)
            .build();

    @Test
    void getAllTeachers() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));

        List<TeacherDtoFull> targetTeachers = teacherService.getAllTeachers();

        Assertions.assertNotNull(targetTeachers);
        Assertions.assertEquals(1, targetTeachers.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void getTeacherById(){
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        TeacherDtoFull actualTeacher = teacherService.getTeacherById(teacherId);

        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
    }

    @Test
    void saveTeacher() {
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDtoFull savedTeacher = teacherService.saveTeacher(teacherDtoFull);

        Assertions.assertEquals(teacherDtoFull, savedTeacher);
    }

    @Test
    void saveTeacher_returnIllegalArguments_whenFirstNameIsEmpty() {
        teacherDtoFull.setFirstName("");

        when(teacherRepository.save(any(Teacher.class)))
                .thenThrow(new IllegalArgumentException("Поле Фамилия должно быть заполнено"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> teacherService.saveTeacher(teacherDtoFull));

        Assertions.assertEquals("Поле Фамилия должно быть заполнено", exception.getMessage());
    }

    @Test
    void saveTeacher_returnIllegalArguments_whenNameIsEmpty() {
        teacherDtoFull.setName("");

        when(teacherRepository.save(any(Teacher.class)))
                .thenThrow(new IllegalArgumentException("Поле Имя должно быть заполнено"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> teacherService.saveTeacher(teacherDtoFull));

        Assertions.assertEquals("Поле Имя должно быть заполнено", exception.getMessage());
    }

    @Test
    void updateTeacher() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        TeacherDtoFull actualTeacher = teacherService.update(teacherId, teacherDtoFull);

        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void updateTeacherNameTest() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDtoFull actualTeacher = teacherService.updateTeacherName(teacherId, "Miha");
        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
        Assertions.assertEquals(teacher.getName(), actualTeacher.getName());
    }

    @Test
    void updateTeacherFirsName() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDtoFull actualTeacher = teacherService.updateTeacherFirsName(teacherId, "Gotovchits");

        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
        Assertions.assertEquals(teacher.getFirstName(), actualTeacher.getFirstName());
    }

    @Test
    void updateTeacherSurName() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDtoFull actualTeacher = teacherService.updateTeacherSurName(teacherId, "Valentinovna");

        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
        Assertions.assertEquals(teacher.getSurName(), actualTeacher.getSurName());
    }

    @Test
    void updateTeacherBirthday() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any())).thenReturn(teacher);

        TeacherDtoFull actualTeacher = teacherService.updateTeacherBirthday(teacherId, LocalDate.of(2001, 6, 4));

        Assertions.assertEquals(TeacherMapper.toTeacherDtoFull(teacher), actualTeacher);
        Assertions.assertEquals(teacher.getDateOfBirth(), actualTeacher.getDateOfBirth());
    }

    @Test
    void deleteTeacherById() {
        teacherRepository.deleteById(teacherId);
        verify(teacherRepository, times(1)).deleteById(teacherId);
    }
}
