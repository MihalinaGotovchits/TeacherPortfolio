package com.example.teacherportfolio.category.service;

import com.example.teacherportfolio.category.dto.CategoryDto;
import com.example.teacherportfolio.category.mapper.CategoryMapper;
import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.category.repository.CategoryRepository;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.exception.ConflictException;
import com.example.teacherportfolio.teacher.exception.NotExistForTeacherException;
import com.example.teacherportfolio.teacher.exception.NotFoundException;
import com.example.teacherportfolio.teacher.mapper.TeacherMapper;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public CategoryDto getCategoryByTeacherId(UUID teacherId) {
        Teacher teacher = checkTeacher(teacherId);
        log.info("Поиск категории преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());
        Category category = teacher.getCategory();
        if (category == null) {
            throw new NotFoundException(
                    String.format("Категория преподавателя %s %s не найдена",
                            teacher.getName(),
                            teacher.getFirstName()));
        }
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public List<TeacherDtoFull> getByCategory_CategoryLevel(String categoryLevel) {
        log.info("Извлечение списка преподавателей с {} категорией", categoryLevel);
        return teacherRepository.findByCategory_CategoryLevel(categoryLevel).stream()
                .map(TeacherMapper::toTeacherDtoFull).collect(Collectors.toList());
    }

    @Override
    public CategoryDto saveCategory(UUID teacherId, CategoryDto categoryDto) {
        Teacher teacher = checkTeacher(teacherId);

        log.info("Сохранение категории преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());

        Category category = CategoryMapper.toCategory(categoryDto);
        if (teacher.getCategory().equals(category)) {
            throw new ConflictException("Преподавателю уже установлена категория с данными реквизитами");
        }
        category = categoryRepository.save(category);

        teacher.setCategory(category);
        teacherRepository.save(teacher);

        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategoryById(UUID teacherId, CategoryDto categoryDto) {
        Teacher teacher = checkTeacher(teacherId);

        log.info("Обновление категории преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());

        Category existCategory = categoryRepository.findById(categoryDto.getId()).orElseThrow(
                () -> new NotFoundException(String.format("Категория с  Id %s не найдена", categoryDto.getId()))
        );
        if (!teacher.getCategory().getId().equals(categoryDto.getId())) {
            throw new NotExistForTeacherException(
                    String.format("Категория с Id %s не пренадлежит преподавателю с Id %s", categoryDto.getId(), teacherId));
        }

        existCategory.setCategoryLevel(categoryDto.getCategoryLevel());
        existCategory.setDocumentOnAssignmentOfCategory(categoryDto.getDocumentOnAssignmentOfCategory());
        existCategory.setNumberDocumentOnAssignmentOfCategory(categoryDto.getNumberDocumentOnAssignmentOfCategory());
        existCategory.setDateDocumentOnAssignmentOfCategory(categoryDto.getDateDocumentOnAssignmentOfCategory());

        validateCategoryDates(existCategory);

        return CategoryMapper.toCategoryDto(categoryRepository.save(existCategory));
    }

    @Override
    @Transactional
    public void deleteCategoryByUserId(UUID teacherId) {
        Teacher teacher = checkTeacher(teacherId);

        log.info("Удаление категории преподавателя {}{}{}", teacher.getFirstName(), teacher.getName(), teacher.getSurName());

        Category category = teacher.getCategory();

        Category defaultCategory = new Category(category.getId(),
                CategoryLevel.UNCATEGORIZED,
                "Автоматически создана",
                "0",
                LocalDate.now(),
                new ArrayList<>());

        teacher.setCategory(defaultCategory);
        teacherRepository.save(teacher);
        categoryRepository.save(defaultCategory);
    }

    private Teacher checkTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException(String.format("Преподаватель с Id %s не найден", teacherId))
        );
    }

    private void validateCategoryDates(Category category) {
        if (category.getDateDocumentOnAssignmentOfCategory().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Дата присвоения категории не может быть в будущем");
        }
    }
}
