package com.example.teacherportfolio.category.dto;

import com.example.teacherportfolio.category.model.CategoryLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryShortDto {
    private UUID id;

    private CategoryLevel categoryLevel;
}
