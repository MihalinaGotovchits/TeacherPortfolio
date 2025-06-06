package com.example.teacherportfolio.category.dto;

import com.example.teacherportfolio.category.model.CategoryLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {
    @NotNull(message = "Уровень категории обязателен")
    private CategoryLevel categoryLevel;

    @NotBlank(message = "Название документа обязательно")
    private String documentName;

    @NotBlank(message = "Номер документа обязателен")
    private String documentNumber;

    @NotNull(message = "Дата документа обязательна")
    @PastOrPresent(message = "Дата документа не может быть в будущем")
    private LocalDate documentDate;

    @NotNull(message = "ID преподавателя обязательно")
    private Long teacherId;
}