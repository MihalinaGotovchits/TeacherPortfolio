package com.example.teacherportfolio.category.controller;

import com.example.teacherportfolio.category.dto.CategoryRequestDto;
import com.example.teacherportfolio.category.dto.CategoryResponseDto;
import com.example.teacherportfolio.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/teacher/{teacherId}")
    public CategoryResponseDto getCategoryByTeacherId(@PathVariable Long teacherId) {
        //log.info("Поиск категории преподавателя с Id {}", teacherId);
        return categoryService.getCategoryByTeacherId(teacherId);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto saveCategory(
            @PathVariable Long teacherId,
            @Valid @RequestBody CategoryRequestDto categoryDto) {
        //log.info("Сохранение категории преподавателя с Id {}", teacherId);
        return categoryService.createCategory(teacherId, categoryDto);
    }

    @PatchMapping("/teacher/{teacherId}")
    public CategoryResponseDto updateCategoryById(
            @PathVariable Long teacherId,
            @Valid @RequestBody CategoryRequestDto categoryDto) {
        //log.info("Обновление категории преподавателя с Id {}", teacherId);
        return categoryService.updateCategory(teacherId, categoryDto);
    }

    @DeleteMapping("/{teacherId}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryByUserId(@PathVariable Long teacherId) {
        //log.info("Удаление категории преподавателя с Id {}", teacherId);
        categoryService.deleteCategory(teacherId);
    }
}
