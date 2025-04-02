package com.example.teacherportfolio.teacher.model;

import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.education.model.Education;
import com.example.teacherportfolio.refresherCourses.model.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "teacher_id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sur_name")
    private String surName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "subject", nullable = false)
    private Subject subject;


    @Column(name = "work_experience")
    private Integer workExperience;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> education;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "teacher_courses",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    //педагогическая нагрузка
}
