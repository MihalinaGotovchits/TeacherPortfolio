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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotBlank
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotNull
    @Column(name = "count_of_hours", nullable = false)
    private int countOfHours;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotBlank
    @Column(name = "organization", nullable = false)
    private String organization;

    @NotBlank
    @Column(name = "certificate_number", nullable = false)
    private String certificateNumber;

    @NotNull
    @Column(name = "certificate_date", nullable = false)
    private LocalDate certificateDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
