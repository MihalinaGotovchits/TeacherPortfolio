package com.example.teacherportfolio.category.service;

import com.example.teacherportfolio.category.dto.CategoryRequestDto;
import com.example.teacherportfolio.category.dto.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto getCategoryByTeacherId(Long teacherId);
    CategoryResponseDto createCategory(Long teacherId, CategoryRequestDto requestDto);
    CategoryResponseDto updateCategory(Long categoryId, CategoryRequestDto requestDto);
    void deleteCategory(Long teacherId);
}