package com.example.teacherportfolio.refresherCourses.dto;

import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CoursesDto {
    private UUID id;

    private String nameOfCourse;

    private int countOfHours;

    private LocalDate startOfTraining;

    private LocalDate endOfTraining;

    private String nameOfEducationalInstitution;

    private String numberOfCertificate;

    private LocalDate dateOfCertificate;

    private Set<Teacher> teachers;
}
