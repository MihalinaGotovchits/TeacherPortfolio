package com.example.teacherportfolio.category.model;

import com.example.teacherportfolio.teacher.model.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "category_id")
    private UUID id;

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

    @OneToMany(mappedBy = "category")
    private List<Teacher> teachers;
}
