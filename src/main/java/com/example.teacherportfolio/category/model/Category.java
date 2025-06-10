package com.example.teacherportfolio.category.model;

import com.example.teacherportfolio.teacher.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category_level", nullable = false)
    private CategoryLevel categoryLevel;

    @NotBlank
    @Column(name = "document_on_assignment", nullable = false)
    private String documentOnAssignmentOfCategory;

    @NotBlank
    @Column(name = "number_document_on_assignment", nullable = false)
    private String numberDocumentOnAssignmentOfCategory;

    @NotNull
    @Column(name = "date_document_on_assignment", nullable = false)
    private LocalDate dateDocumentOnAssignmentOfCategory;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
}
