package com.example.teacherportfolio.refresherCourses.model;

import com.example.teacherportfolio.teacher.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "course_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name_of_course", nullable = false)
    private String nameOfCourse;

    @NotNull
    @Column(name = "count_of_hours", nullable = false)
    private int countOfHours;

    @NotNull
    @Column(name = "start_of_training", nullable = false)
    private LocalDate startOfTraining;

    @NotNull
    @Column(name = "end_of_training", nullable = false)
    private LocalDate endOfTraining;

    @NotBlank
    @Column(name = "name_of_educational_institution", nullable = false)
    private String nameOfEducationalInstitution;

    @NotBlank
    @Column(name = "number_of_certificate", nullable = false)
    private String numberOfCertificate;

    @NotNull
    @Column(name = "date_of_certificate", nullable = false)
    private LocalDate dateOfCertificate;

    @ManyToMany(mappedBy = "courses")
    private Set<Teacher> teachers;
}
