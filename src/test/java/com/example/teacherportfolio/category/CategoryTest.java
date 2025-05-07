package com.example.teacherportfolio.category;

import com.example.teacherportfolio.category.dto.CategoryDto;
import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.category.model.CategoryLevel;
import com.example.teacherportfolio.category.repository.CategoryRepository;
import com.example.teacherportfolio.category.service.CategoryServiceImpl;
import com.example.teacherportfolio.teacher.dto.TeacherDtoFull;
import com.example.teacherportfolio.teacher.model.Subject;
import com.example.teacherportfolio.teacher.model.Teacher;
import com.example.teacherportfolio.teacher.repository.TeacherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private TeacherRepository teacherRepository;

    private final UUID teacherId = UUID.randomUUID();
    private final UUID randomUUIDForCategory = UUID.randomUUID();
    private final Category category = new Category(randomUUIDForCategory,
            CategoryLevel.FIRST,
            "Распоряжение",
            "A-29",
            LocalDate.of(2025, 4, 2),
            new ArrayList<>());

    private final CategoryDto categoryDto = new CategoryDto(randomUUIDForCategory,
            CategoryLevel.FIRST,
            "Распоряжение",
            "A-29",
            LocalDate.of(2025, 4, 2),
            new ArrayList<>());

    private final Teacher teacher = Teacher.builder()
            .id(teacherId)
            .firstName("FirstName")
            .name("Name")
            .surName("SurName")
            .dateOfBirth(LocalDate.of(1995, 1, 12))
            .subject(Subject.STRING)
            .category(category)
            .education(new ArrayList<>())
            .courses(new ArrayList<>())
            .workExperience(7)
            .build();

    @Test
    void getCategoryByUserId() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        CategoryDto result = categoryService.getCategoryByTeacherId(teacherId);

        Assertions.assertEquals(categoryDto, result);
    }

    @Test
    void getByCategory_CategoryLevel() {
        when(teacherRepository.findByCategory_CategoryLevel(category.getCategoryLevel().toString())).thenReturn(List.of(teacher));

        List<TeacherDtoFull> result = categoryService.getByCategory_CategoryLevel(category.getCategoryLevel().toString());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.size());
        verify(teacherRepository, times(1)).findByCategory_CategoryLevel(category.getCategoryLevel().toString());
    }

    @Test
    void saveCategory() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(categoryRepository.save(any())).thenReturn(category);

        CategoryDto actualCategory = categoryService.saveCategory(teacherId, categoryDto);

        Assertions.assertEquals(categoryDto, actualCategory);
    }

    @Test
    void updateCategoryById() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryDto actualCategory = categoryService.updateCategoryById(teacherId, categoryDto);

        Assertions.assertEquals(categoryDto, actualCategory);
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(categoryRepository, times(1)).findById(category.getId());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategoryByUserId_ShouldSetDefaultCategory() {
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        Category defaultCategory = new Category(category.getId(),
                CategoryLevel.UNCATEGORIZED,
                "Автоматически создана",
                "0",
                LocalDate.now(),
                new ArrayList<>());

        categoryService.deleteCategoryByUserId(teacherId);

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(teacherRepository, times(1)).save(teacher);
        verify(categoryRepository, times(1)).save(argThat(cat ->
                cat.getCategoryLevel() == CategoryLevel.UNCATEGORIZED &&
                        cat.getDocumentOnAssignmentOfCategory().equals("Автоматически создана") &&
                        cat.getNumberDocumentOnAssignmentOfCategory().equals("0")
        ));

        Assertions.assertEquals(teacher.getCategory().getCategoryLevel().toString(), defaultCategory.getCategoryLevel().toString());
        Assertions.assertEquals(teacher.getCategory().getDocumentOnAssignmentOfCategory(), defaultCategory.getDocumentOnAssignmentOfCategory());
        Assertions.assertEquals(teacher.getCategory().getNumberDocumentOnAssignmentOfCategory(), defaultCategory.getNumberDocumentOnAssignmentOfCategory());
    }
}
