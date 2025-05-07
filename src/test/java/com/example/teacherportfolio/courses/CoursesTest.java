package com.example.teacherportfolio.courses;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.refresherCourses.dto.CoursesDto;
import com.example.teacherportfolio.refresherCourses.mapper.CoursesMapper;
import com.example.teacherportfolio.refresherCourses.model.Course;
import com.example.teacherportfolio.refresherCourses.repository.CoursesRepository;
import com.example.teacherportfolio.refresherCourses.service.CoursesServiceImpl;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.model.Subject;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CoursesTest {
    @Mock
    private CoursesRepository coursesRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @InjectMocks
    private CoursesServiceImpl coursesService;

    private final UUID teacherId = UUID.randomUUID();
    private final UUID randomUUIDForCategory = UUID.randomUUID();

    private final UUID randomUUIDForCourse = UUID.randomUUID();
    private final Category category = new Category(randomUUIDForCategory,
            CategoryLevel.FIRST,
            "Распоряжение",
            "A-29",
            LocalDate.of(2025,4,2),
            new ArrayList<>());

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

    private Set<Teacher> teachers = new HashSet<>();

    private final Course course = Course.builder()
            .id(randomUUIDForCourse)
            .nameOfCourse("Course")
            .countOfHours(72)
            .nameOfEducationalInstitution("Institute")
            .startOfTraining(LocalDate.of(2025, 1, 25))
            .endOfTraining(LocalDate.of(2025, 1, 27))
            .numberOfCertificate("45")
            .dateOfCertificate(LocalDate.of(2025,1,28))
            .teachers(new HashSet<>())
            .build();

    private void init() {
        teachers.add(teacher);
        List<Course> courses = teacher.getCourses();
        courses.add(course);
        teacher.setCourses(courses);
    }

    @Test
    void getAllCoursesTest() {
        when(coursesRepository.findAll()).thenReturn(List.of(course));

        List<CoursesDto> coursesList = coursesService.getAllCourses();

        Assertions.assertNotNull(coursesList);
        Assertions.assertEquals(1, coursesList.size());
        verify(coursesRepository, times(1)).findAll();
    }

    @Test
    void getCourseByIdTest() {
        when(coursesRepository.findById(course.getId())).thenReturn(Optional.of(course));

        CoursesDto coursesDto = coursesService.getCourseById(course.getId());

        Assertions.assertEquals(CoursesMapper.toRefreshCoursesDto(course), coursesDto);
    }

    @Test
    void getCoursesByTeacherIdTest() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findCoursesByTeacherId(teacher.getId())).thenReturn(List.of(course));

        List<CoursesDto> coursesDto = coursesService.getCoursesByTeacherId(teacherId);

        Assertions.assertNotNull(coursesDto);
        Assertions.assertEquals(1, coursesDto.size());

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherRepository, times(1)).findCoursesByTeacherId(teacherId);
    }

    @Test
    void getTeachersByCourseIdTest() {
        when(coursesRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(coursesRepository.findTeachersByCourseId(course.getId())).thenReturn(List.of(teacher));

        List<TeacherDtoFull> teachersList = coursesService.getTeachersByCourseId(course.getId());

        Assertions.assertNotNull(teachersList);
        Assertions.assertEquals(1, teachersList.size());
        verify(coursesRepository, times(1)).findTeachersByCourseId(course.getId());
    }

    @Test
    void saveCourseByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(coursesRepository.save(any())).thenReturn(course);

        CoursesDto actualCourse = coursesService.saveCourseByTeacherId(teacherId, CoursesMapper.toRefreshCoursesDto(course));

        Assertions.assertEquals(CoursesMapper.toRefreshCoursesDto(course), actualCourse);
    }

    @Test
    void updateCoursesByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(coursesRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(coursesRepository.save(any())).thenReturn(course);

        CoursesDto coursesDto = coursesService.updateCoursesByTeacherId(teacherId, CoursesMapper.toRefreshCoursesDto(course));

        Assertions.assertEquals(CoursesMapper.toRefreshCoursesDto(course), coursesDto);
    }

    @Test
    void updateTeacherCourseByNameTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(coursesRepository.findByTeacherIdAndCourseName(teacherId, course.getNameOfCourse())).thenReturn(Optional.of(course));
        when(coursesRepository.save(any())).thenReturn(course);

        CoursesDto coursesDto = coursesService.updateTeacherCourseByName(
                teacherId,
                course.getNameOfCourse(),
                CoursesMapper.toRefreshCoursesDto(course));

        Assertions.assertEquals(CoursesMapper.toRefreshCoursesDto(course), coursesDto);
    }

    @Test
    void deleteCourseByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(coursesRepository.findById(course.getId())).thenReturn(Optional.of(course));

        coursesService.deleteCourseByTeacherId(teacherId, course.getId());
    }

    @Test
    void deleteAllCoursesByTeacherId() {
      init();

      when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

      coursesService.deleteAllCoursesByTeacherId(teacherId);
    }
}
