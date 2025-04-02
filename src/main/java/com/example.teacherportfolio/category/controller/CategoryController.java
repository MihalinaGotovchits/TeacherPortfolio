package com.example.teacherportfolio.category.controller;

import com.example.teacherportfolio.category.dto.CategoryDto;
import com.example.teacherportfolio.category.service.CategoryService;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/teacher/{teacherId}")
    public CategoryDto getCategoryByTeacherId(@PathVariable UUID teacherId) {
        log.info("Поиск категории преподавателя с Id {}", teacherId);
        return categoryService.getCategoryByTeacherId(teacherId);
    }

    @GetMapping("/level/{categoryLevel}")
    public List<TeacherDtoFull> getByCategory_CategoryLevel(@PathVariable String categoryLevel) {
        log.info("Извлечение списка преподавателей с {} категорией", categoryLevel);
        return categoryService.getByCategory_CategoryLevel(categoryLevel);
    }

    @PostMapping("/teacher/{teacherId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto saveCategory(
            @PathVariable UUID teacherId,
            @RequestBody CategoryDto categoryDto) {
        log.info("Сохранение категории преподавателя с Id {}", teacherId);
        return categoryService.saveCategory(teacherId, categoryDto);
    }

    @PutMapping("/teacher/{teacherId}")
    public CategoryDto updateCategoryById(
            @PathVariable UUID teacherId,
            @RequestBody CategoryDto categoryDto) {
        log.info("Обновление категории преподавателя с Id {}", teacherId);
        return categoryService.updateCategoryById(teacherId, categoryDto);
    }

    @DeleteMapping("/teacher/{teacherId}")
    @Transactional
    public void deleteCategoryByUserId(@PathVariable UUID teacherId) {
        log.info("Удаление категории преподавателя с Id {}", teacherId);
        categoryService.deleteCategoryByUserId(teacherId);
    }
}
