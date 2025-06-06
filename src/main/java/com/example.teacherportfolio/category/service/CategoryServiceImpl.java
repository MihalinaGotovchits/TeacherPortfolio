package com.example.teacherportfolio.category.service;

import com.example.teacherportfolio.category.dto.CategoryRequestDto;
import com.example.teacherportfolio.category.dto.CategoryResponseDto;
import com.example.teacherportfolio.category.mapper.CategoryMapper;
import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.category.repository.CategoryRepository;
import com.example.teacherportfolio.teacher.exception.ConflictException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TeacherRepository teacherRepository;

    @Override
    @Transactional(readOnly = true)
    public CategoryResponseDto getCategoryByTeacherId(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        log.info("Получение категории преподавателя ID: {}", teacherId);

        Category category = teacher.getCategories().getLast();
        if (category == null) {
            throw new NotFoundException(
                    String.format("Преподаватель ID %s не имеет категории", teacherId));
        }

        return CategoryMapper.toDto(category);
    }

    @Override
    @Transactional
    public CategoryResponseDto createCategory(Long teacherId, CategoryRequestDto requestDto) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        log.info("Создание категории для преподавателя ID: {}", teacherId);

        validateCategoryDate(requestDto.getDocumentDate());

        List<Category> categories = teacher.getCategories();

        Category category = categories.getFirst();

        if (category.getCategoryLevel().equals(CategoryLevel.FIRST) || category.getCategoryLevel().equals(CategoryLevel.HIGHER)) {
            throw new ConflictException("Преподаватель уже имеет категорию");
        }

        List<Category> uncategorized = categories.stream()
                .filter(c -> CategoryLevel.UNCATEGORIZED.equals(c.getCategoryLevel()))
                .toList();

        Category uncategorizedCategory = uncategorized.getFirst();

        if (uncategorizedCategory.getCategoryLevel().equals(CategoryLevel.UNCATEGORIZED)) {
            categoryRepository.delete(uncategorizedCategory);
            Category newCategory = CategoryMapper.toEntity(requestDto, teacher);
            Category savedCategory = categoryRepository.save(newCategory);
            teacher.getCategories().clear();
            teacher.getCategories().add(savedCategory);
            return CategoryMapper.toDto(savedCategory);
        }

        Category newCategory = CategoryMapper.toEntity(requestDto, teacher);
        Category savedCategory = categoryRepository.save(newCategory);
        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    @Transactional
    public CategoryResponseDto updateCategory(Long teacherId, CategoryRequestDto requestDto) {
        log.info("Обновление категории для преподавателя с ID: {}", teacherId);
        Teacher existingTeacher = getTeacherOrThrow(teacherId);

        validateCategoryDate(requestDto.getDocumentDate());

        List<Category> categories = existingTeacher.getCategories();

        Category existingCategory = categories.getFirst();

        existingCategory.setCategoryLevel(requestDto.getCategoryLevel());
        existingCategory.setDocumentOnAssignmentOfCategory(requestDto.getDocumentName());
        existingCategory.setNumberDocumentOnAssignmentOfCategory(requestDto.getDocumentNumber());
        existingCategory.setDateDocumentOnAssignmentOfCategory(requestDto.getDocumentDate());

        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void deleteCategory(Long teacherId) {
        Teacher teacher = getTeacherOrThrow(teacherId);
        log.info("Удаление категории преподавателя ID: {}", teacherId);

        List<Category> categories = teacher.getCategories();
        if (categories.isEmpty()) {
            throw new NotFoundException("Преподаватель не имеет категории для удаления");
        }

        Category currentCategory = categories.get(0);

        Category defaultCategory = Category.builder()
                .categoryLevel(CategoryLevel.UNCATEGORIZED)
                .documentOnAssignmentOfCategory("Автоматически создана")
                .numberDocumentOnAssignmentOfCategory("0")
                .dateDocumentOnAssignmentOfCategory(LocalDate.now())
                .teacher(teacher)
                .build();

        Category savedDefaultCategory = categoryRepository.save(defaultCategory);

        categoryRepository.delete(currentCategory);

        teacher.getCategories().clear();
        teacher.getCategories().add(savedDefaultCategory);
        teacherRepository.save(teacher);
    }

    private Teacher getTeacherOrThrow(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Преподаватель ID %s не найден", teacherId)));
    }

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Категория ID %s не найдена", categoryId)));
    }

    private void validateCategoryDate(LocalDate date) {
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Дата документа не может быть в будущем");
        }
    }
}