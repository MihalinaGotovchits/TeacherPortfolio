package com.example.teacherportfolio.category.mapper;

import com.example.teacherportfolio.category.dto.CategoryDto;
import com.example.teacherportfolio.category.dto.CategoryShortDto;
import com.example.teacherportfolio.category.model.Category;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryLevel(category.getCategoryLevel())
                .documentOnAssignmentOfCategory(category.getDocumentOnAssignmentOfCategory())
                .dateDocumentOnAssignmentOfCategory(category.getDateDocumentOnAssignmentOfCategory())
                .numberDocumentOnAssignmentOfCategory(category.getNumberDocumentOnAssignmentOfCategory())
                .teachers(category.getTeachers())
                .build();
    }

    public static CategoryShortDto toCategoryShortDto(Category category) {
        return CategoryShortDto.builder()
                .id(category.getId())
                .categoryLevel(category.getCategoryLevel())
                .build();
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .categoryLevel(categoryDto.getCategoryLevel())
                .documentOnAssignmentOfCategory(categoryDto.getDocumentOnAssignmentOfCategory())
                .numberDocumentOnAssignmentOfCategory(categoryDto.getNumberDocumentOnAssignmentOfCategory())
                .dateDocumentOnAssignmentOfCategory(categoryDto.getDateDocumentOnAssignmentOfCategory())
                .teachers(categoryDto.getTeachers())
                .build();
    }
}
