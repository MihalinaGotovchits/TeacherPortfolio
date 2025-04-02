package com.example.teacherportfolio.category.dto;

import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class CategoryDto {
    private UUID id;

    private CategoryLevel categoryLevel;

    private String documentOnAssignmentOfCategory;

    private String numberDocumentOnAssignmentOfCategory;

    private LocalDate dateDocumentOnAssignmentOfCategory;

    private List<Teacher> teachers;
}
