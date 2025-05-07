package com.example.teacherportfolio.teacher;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.teacher.controller.TeacherController;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.exception.IllegalArgumentException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.model.Subject;
import com.example.teacherportfolio.teacher.service.TeacherServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
@ExtendWith(MockitoExtension.class)
public class TeacherControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TeacherServiceImpl teacherService;

    private final UUID teacherId = UUID.randomUUID();
    private final UUID randomUUIDForCategory = UUID.randomUUID();
    private final Category category = new Category(randomUUIDForCategory,
            CategoryLevel.FIRST,
            "Распоряжение",
            "A-29",
            LocalDate.of(2025, 4, 2),
            new ArrayList<>());

    private final TeacherDtoFull teacher = TeacherDtoFull.builder()
            .id(teacherId)
            .firstName("FirstName")
            .name("Name")
            .surName("SurName")
            .dateOfBirth(LocalDate.of(1995, 1, 12))
            .subject(Subject.STRING)
            .category(category)
            .education(new ArrayList<>())
            .refresherCoursesList(new ArrayList<>())
            .workExperience(7)
            .build();

    @Test
    void getAllTeachersTest() throws Exception {
        List<TeacherDtoFull> teachersList = Collections.singletonList(teacher);
        when(teacherService.getAllTeachers()).thenReturn(teachersList);

        mockMvc.perform(get("/api/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(teacher.getId().toString()))
                .andExpect(jsonPath("$[0].firstName", is(teacher.getFirstName())))
                .andExpect(jsonPath("$[0].name", is(teacher.getName())))
                .andExpect(jsonPath("$[0].surName", is(teacher.getSurName())))
                .andExpect(jsonPath("$[0].dateOfBirth", is(teacher.getDateOfBirth().toString())))
                .andExpect(jsonPath("$[0].subject", is(teacher.getSubject().toString())))
                .andExpect(jsonPath("$[0].workExperience", is(teacher.getWorkExperience())));
    }

    @Test
    void getTeacherById_shouldReturnTeacher_whenTeacherExists() throws Exception {
        when(teacherService.getTeacherById(teacherId)).thenReturn(teacher);

        mockMvc.perform((get("/api/teachers/{id}", teacherId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacher.getId().toString()))
                .andExpect(jsonPath("$.firstName", is(teacher.getFirstName())))
                .andExpect(jsonPath("$.name", is(teacher.getName())))
                .andExpect(jsonPath("$.surName", is(teacher.getSurName())))
                .andExpect(jsonPath("$.dateOfBirth", is(teacher.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.subject", is(teacher.getSubject().toString())))
                .andExpect(jsonPath("$.workExperience", is(teacher.getWorkExperience())));
    }

    @Test
    void getTeacherById_shouldReturnNotFound_whenTeacherDoesNotExist() throws Exception {
        UUID nonExistentId = UUID.randomUUID();
        when(teacherService.getTeacherById(nonExistentId)).thenThrow(new NotFoundException("Teacher not found"));

        mockMvc.perform(get("/api/teachers/{id}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveTeacher() throws Exception {
        when(teacherService.saveTeacher(any())).thenReturn(teacher);

        mockMvc.perform(post("/api/teachers")
                        .content(objectMapper.writeValueAsString(teacher))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(teacher.getId().toString()))
                .andExpect(jsonPath("$.firstName", is(teacher.getFirstName())))
                .andExpect(jsonPath("$.name", is(teacher.getName())))
                .andExpect(jsonPath("$.surName", is(teacher.getSurName())))
                .andExpect(jsonPath("$.dateOfBirth", is(teacher.getDateOfBirth().toString())))
                .andExpect(jsonPath("$.subject", is(teacher.getSubject().toString())))
                .andExpect(jsonPath("$.workExperience", is(teacher.getWorkExperience())));
    }

    @Test
    void saveTeacher_shouldReturnIllegalArguments_whenTeacherDoesNotExistFirstName() throws Exception {
        teacher.setFirstName("");
        when(teacherService.saveTeacher(any())).thenThrow(new IllegalArgumentException("Поле фамилия должно быть заполненно"));

        mockMvc.perform(post("/api/teachers")
                        .content(objectMapper.writeValueAsString(teacher))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveTeacher_shouldReturnIllegalArguments_whenTeacherDoesNotExistName() throws Exception {
        teacher.setName("");
        when(teacherService.saveTeacher(any())).thenThrow(new IllegalArgumentException("Поле Имя должно быть заполненно"));

        mockMvc.perform(post("/api/teachers")
                        .content(objectMapper.writeValueAsString(teacher))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateTeacher() throws Exception {
        TeacherDtoFull forUpdate = TeacherDtoFull.builder()
                .id(teacherId)
                .firstName("First")
                .name("newName")
                .subject(Subject.PIANO)
                .surName("newSurName")
                .dateOfBirth(LocalDate.of(1992, 2, 18))
                .workExperience(9)
                .refresherCoursesList(new ArrayList<>())
                .category(new Category())
                .education(new ArrayList<>())
                .build();

        when(teacherService.update(eq(teacherId), any(TeacherDtoFull.class))).thenReturn(teacher);

        mockMvc.perform(patch("/api/teachers/{id}", teacherId)
                        .content(objectMapper.writeValueAsString(forUpdate))  // send forUpdate instead of teacher
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacher.getId().toString()))
                .andExpect(jsonPath("$.firstName").value(forUpdate.getFirstName()))
                .andExpect(jsonPath("$.name").value(forUpdate.getName()))
                .andExpect(jsonPath("$.surName").value(forUpdate.getSurName()))
                .andExpect(jsonPath("$.dateOfBirth").value(forUpdate.getDateOfBirth().toString()))
                .andExpect(jsonPath("$.subject").value(teacher.getSubject().toString()))
                .andExpect(jsonPath("$.workExperience").value(teacher.getWorkExperience()));
    }
}
