package com.example.teacherportfolio.education;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.education.dto.EducationDto;
import com.example.teacherportfolio.education.mapper.EducationMapper;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.education.model.LevelOfEducation;
import com.example.teacherportfolio.education.repository.EducationRepository;
import com.example.teacherportfolio.education.service.EducationServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EducationTest {
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private EducationRepository educationRepository;
    @InjectMocks
    private EducationServiceImpl educationService;

    private final UUID teacherId = UUID.randomUUID();
    private final UUID randomUUIDForCategory = UUID.randomUUID();

    private final UUID randomUUIDForEducation = UUID.randomUUID();
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

    private final Education education = Education.builder()
            .id(randomUUIDForEducation)
            .levelOfEducation(LevelOfEducation.HIGHER)
            .speciality("teacher")
            .qualification("music")
            .endDate(LocalDate.of(2016,6,14))
            .diplomaNumber("A-29")
            .nameOfEducationalInstitution("Institute")
            .teacher(teacher)
            .build();

    void init() {
        List<Education> educationList = teacher.getEducation();
        educationList.add(education);
        teacher.setEducation(educationList);
    }

    @Test
    void getAllEducationsTest() {
        init();

        when(educationRepository.findAll()).thenReturn(List.of(education));

        List<EducationDto> educationDto = educationService.getAllEducations();

        Assertions.assertNotNull(educationDto);
        Assertions.assertEquals(1, educationDto.size());

        verify(educationRepository, times(1)).findAll();
    }

    @Test
    void getEducationsByTeacherIdAndLevelTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(educationRepository.findByTeacherIdAndLevelOfEducation(teacherId, LevelOfEducation.HIGHER)).thenReturn(List.of(education));

        List<EducationDto> educationList = educationService.getEducationsByTeacherIdAndLevel(teacherId, LevelOfEducation.HIGHER);

        Assertions.assertNotNull(educationList);
        Assertions.assertEquals(1,educationList.size());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(educationRepository, times(1)).findByTeacherIdAndLevelOfEducation(teacherId, LevelOfEducation.HIGHER);
    }

    @Test
    void getEducationsByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(educationRepository.findByTeacherId(teacherId)).thenReturn(List.of(education));

        List<EducationDto> educationDtoList = educationService.getEducationsByTeacherId(teacherId);

        Assertions.assertNotNull(educationDtoList);
        Assertions.assertEquals(1, educationDtoList.size());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(educationRepository, times(1)).findByTeacherId(teacherId);
    }

    @Test
    void saveEducationByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(educationRepository.save(any())).thenReturn(education);

        EducationDto educationDto = educationService.saveEducationByTeacherId(teacherId, EducationMapper.toEducationDto(education));

        Assertions.assertEquals(EducationMapper.toEducationDto(education), educationDto);
    }

    @Test
    void updateTeacherEducationTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(educationRepository.findById(education.getId())).thenReturn(Optional.of(education));
        when(educationRepository.save(any())).thenReturn(education);

        EducationDto educationDto = educationService.updateTeacherEducation(teacherId, EducationMapper.toEducationDto(education));

        Assertions.assertEquals(EducationMapper.toEducationDto(education), educationDto);
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(educationRepository, times(1)).findById(education.getId());
        verify(educationRepository, times(1)).save(education);
    }
    @Test
    void deleteEducationByTeacherIdTest() {
        init();

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(educationRepository.findById(education.getId())).thenReturn(Optional.of(education));

        educationService.deleteEducationByTeacherId(teacherId, education.getId());

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(educationRepository, times(1)).findById(education.getId());
        verify(educationRepository, times(1)).deleteById(education.getId());
    }
}
