package com.example.teacherportfolio.category.mapper;

import com.example.teacherportfolio.category.dto.CategoryRequestDto;
import com.example.teacherportfolio.category.dto.CategoryResponseDto;
import com.example.teacherportfolio.category.model.Category;
import com.example.teacherportfolio.teacher.model.Teacher;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CategoryMapper {
    public static Category toEntity(CategoryRequestDto dto, Teacher teacher) {
        return Category.builder()
                .categoryLevel(dto.getCategoryLevel())
                .documentOnAssignmentOfCategory(dto.getDocumentName())
                .numberDocumentOnAssignmentOfCategory(dto.getDocumentNumber())
                .dateDocumentOnAssignmentOfCategory(dto.getDocumentDate())
                .teacher(teacher)
                .build();
    }

    public static CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId())
                .categoryLevel(category.getCategoryLevel())
                .documentName(category.getDocumentOnAssignmentOfCategory())
                .documentNumber(category.getNumberDocumentOnAssignmentOfCategory())
                .documentDate(category.getDateDocumentOnAssignmentOfCategory())
                .teacherId(category.getTeacher().getId())
                .teacherFullName(getTeacherFullName(category.getTeacher()))
                .build();
    }

//    public static CategoryShortResponseDto toShortDto(Category category) {
//        return CategoryShortResponseDto.builder()
//                .id(category.getId())
//                .categoryLevel(category.getCategoryLevel())
//                .build();
//    }

    private static String getTeacherFullName(Teacher teacher) {
        return String.format("%s %s %s",
                teacher.getLastName(),
                teacher.getFirstName(),
                teacher.getSurName()).trim();
    }
}