package com.example.teacherportfolio.category.service;

import com.example.teacherportfolio.category.dto.CategoryDto;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    //метод на извлечение преподавателей только с высшей категорией
    //метод на извлечение преподавателей только с первой категорией
    //метод на извлечение преподавателей без категории
    CategoryDto getCategoryByTeacherId(UUID teacherId);

    List<TeacherDtoFull> getByCategory_CategoryLevel(String categoryLevel);

    CategoryDto saveCategory(UUID teacherId, CategoryDto categoryDto);

    CategoryDto updateCategoryById(UUID teacherId, CategoryDto categoryDto);

    void deleteCategoryByUserId(UUID teacherId);
}
