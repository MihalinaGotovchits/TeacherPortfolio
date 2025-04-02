package com.example.teacherportfolio.education.model;

import com.example.teacherportfolio.teacher.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "educations")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "education_id")
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "level_of_education", nullable = false)
    private LevelOfEducation levelOfEducation;

    @NotBlank
    @Column(name = "name_of_educational_institution", nullable = false, length = 1012)
    private String nameOfEducationalInstitution;

    @NotBlank
    @Column(nullable = false, length = 512)
    private String speciality;

    @NotBlank
    @Column(nullable = false, length = 512)
    private String qualification;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @NotBlank
    @Column(name = "diploma_number", nullable = false)
    private String diplomaNumber;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
