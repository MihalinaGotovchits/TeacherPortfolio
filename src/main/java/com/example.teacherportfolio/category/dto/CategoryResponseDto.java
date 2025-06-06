package com.example.teacherportfolio.category.dto;

import com.example.teacherportfolio.category.model.CategoryLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private CategoryLevel categoryLevel;
    private String documentName;
    private String documentNumber;
    private LocalDate documentDate;
    private Long teacherId;
    private String teacherFullName;
}